-- docker exec -it scphenotype_mysql bash
-- mysql --local-infile=1 -u root -pscphenotype123456
-- scphenotype123456
set global local_infile = 'ON';
truncate `scphenotype`.`t_single_cell`;
LOAD DATA LOCAL INFILE "/root/Browse/Browse_SC.txt" INTO TABLE `scphenotype`.`t_single_cell` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_gwas`;
LOAD DATA LOCAL INFILE "/root/Browse/Browse_GWAS.txt" INTO TABLE `scphenotype`.`t_gwas` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_cluster_anno`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_TU_table_raw_anno.txt" INTO TABLE `scphenotype`.`t_cluster_anno` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_cluster_cell_count`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_cluster_cellnumber.txt" INTO TABLE `scphenotype`.`t_cluster_cell_count` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_sample_count`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_sample_cellnumber.txt" INTO TABLE `scphenotype`.`t_sample_count` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_go`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_GO.txt" INTO TABLE `scphenotype`.`t_go` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_kegg`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_KEGG.txt" INTO TABLE `scphenotype`.`t_kegg` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_gwas_gse`;
LOAD DATA LOCAL INFILE "/root/heatmap/GWAS_GSE.txt" INTO TABLE `scphenotype`.`t_gwas_gse` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_gwas_chr_counts`;
LOAD DATA LOCAL INFILE "/root/GWAS/all_chr_num.txt" INTO TABLE `scphenotype`.`t_gwas_chr_counts` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
truncate `scphenotype`.`t_gene`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_genes.txt" INTO TABLE `scphenotype`.`t_gene` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gwas_marker_01`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_markers_01.txt" INTO TABLE `scphenotype`.`t_gwas_marker_01` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gwas_marker_05`;
LOAD DATA LOCAL INFILE "/root/SC_all/all_markers_01.txt" INTO TABLE `scphenotype`.`t_gwas_marker_05` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
-- De
truncate `scphenotype`.`t_cell_type_fdr`;
LOAD DATA LOCAL INFILE "/root/heatmap/celltype_fdr.txt" INTO TABLE `scphenotype`.`t_cell_type_fdr` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
-- De
truncate `scphenotype`.`t_cell_type_gse_gwas`;
LOAD DATA LOCAL INFILE "/root/heatmap/celltype_GSE_GWAS.txt" INTO TABLE `scphenotype`.`t_cell_type_gse_gwas` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_cluster_fdr`;
LOAD DATA LOCAL INFILE "/root/heatmap/cluster_fdr.txt" INTO TABLE `scphenotype`.`t_cluster_fdr` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_cluster_gse_gwas`;
LOAD DATA LOCAL INFILE "/root/heatmap/cluster_GSE_GWAS.txt" INTO TABLE `scphenotype`.`t_cluster_gse_gwas` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_single_cell_gene`;
LOAD DATA LOCAL INFILE "/root/feature_plot/sc_gene.txt" INTO TABLE `scphenotype`.`t_single_cell_gene` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gwas_gene`;
LOAD DATA LOCAL INFILE "/root/feature_plot/GWAS_gene.txt" INTO TABLE `scphenotype`.`t_gwas_gene` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gene_info`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/Gene_information4_ok.txt" INTO TABLE `scphenotype`.`t_gene_info` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gene_disease_association`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/disease_association.txt" INTO TABLE `scphenotype`.`t_gene_disease_association` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_gwas_gse_marker`;
-- LOAD DATA LOCAL INFILE "/root/Gene_detail/GWAS_GSE_marker.txt" INTO TABLE `scphenotype`.`t_gwas_gse_marker` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';
LOAD DATA LOCAL INFILE "/root/SC/all_GSE_GWAS_markers.txt" INTO TABLE `scphenotype`.`t_gwas_gse_marker` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_common_snp`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Common_SNP.bed" INTO TABLE `scphenotype`.`t_common_snp` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_crispr`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_CRISPR.bed" INTO TABLE `scphenotype`.`t_crispr` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_enhancer`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Enhancer.bed" INTO TABLE `scphenotype`.`t_enhancer` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_eqtl`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_eQTL.bed" INTO TABLE `scphenotype`.`t_eqtl` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_risk_snp`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Risk_SNP.bed" INTO TABLE `scphenotype`.`t_risk_snp` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_rna_interaction`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_RNA_Interaction.bed" INTO TABLE `scphenotype`.`t_rna_interaction` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_super_enhancer_dbsuper`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Super_Enhancer_dbSUPER.bed" INTO TABLE `scphenotype`.`t_super_enhancer_dbsuper` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_super_enhancer_seav3`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Super_Enhancer_SEAv3.bed" INTO TABLE `scphenotype`.`t_super_enhancer_seav3` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_super_enhancer_sedbv2`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_Super_Enhancer_SEdbv2.bed" INTO TABLE `scphenotype`.`t_super_enhancer_sedbv2` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_tfbs`;
LOAD DATA LOCAL INFILE "/root/Gene_detail/regulation/human_TFBS.bed" INTO TABLE `scphenotype`.`t_tfbs` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_cell_type_differentiation_score`;
LOAD DATA LOCAL INFILE "/root/heatmap/new/celltype_differentiation_scores_all.txt" INTO TABLE `scphenotype`.`t_cell_type_differentiation_score` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

truncate `scphenotype`.`t_cluster_differentiation_score`;
LOAD DATA LOCAL INFILE "/root/heatmap/new/cluster_differentiation_scores_all.txt" INTO TABLE `scphenotype`.`t_cluster_differentiation_score` fields terminated by '\t' optionally enclosed by '"' lines terminated by '\n';

