#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import sys
import random

import anndata as ad
import numpy as np
import pandas as pd
from scipy.sparse import csr_matrix


def get_heatmap(gwas_id_list_str, gse_id_list_str):
    cell_rate = float(cell_rate)
    data = ad.read(trs_file)
    cell_anno = data.obs.copy()

    # 获取细胞类型数量
    cell_type_cluster_count = cell_anno.groupby("cluster")[["UMAP1"]].count().reset_index()
    cell_type_cluster_count.columns = ["cluster", "cluster_size"]
    cell_type_cluster_count["size"] = np.ceil(cell_type_cluster_count["cluster_size"] * cell_rate)

    cell_anno_need = pd.merge(cell_anno, cell_type_cluster_count, on="cluster", how="left")

    cell_anno_need = cell_anno_need[cell_anno_need["index"] <= cell_anno_need["size"]]

    data.var.index = data.var["Sample_ID"]

    name_data = data[:, name]
    # random sample
    # cell_count = int(np.ceil(name_data.shape[0] * cell_rate))
    # random.sample(range(name_data.shape[0]), cell_count)
    # cell_index = np.random.choice(range(name_data.shape[0]), cell_count, replace=False)

    name_data = name_data[cell_anno_need.index, :]

    name_value = name_data.X if value != "pval" else name_data.layers[value]

    values = np.array(csr_matrix(name_data.layers[value]).todense()).flatten()
    values_index = np.argsort(values)
    name_data = name_data[values_index, :]
    print(name_data.obs.index.tolist())
    print(name_data.obs["celltype"].tolist())
    print(name_data.obs["cluster"].tolist())

    if _type_ == "umap":
        print(name_data.obs["UMAP1"].tolist())
        print(name_data.obs["UMAP2"].tolist())
    else:
        print(name_data.obs["TSNE1"].tolist())
        print(name_data.obs["TSNE1"].tolist())

    print(list(values[values_index]))


if __name__ == '__main__':
    get_heatmap(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5])
