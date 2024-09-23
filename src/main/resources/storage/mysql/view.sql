DROP VIEW IF EXISTS t_distinct_cluster_cell_type;
CREATE VIEW t_distinct_cluster_cell_type AS
(
SELECT DISTINCT t_single_cell.f_gse_id, f_cell_type, f_disease
FROM t_cluster_cell_count,
     t_single_cell
WHERE t_cluster_cell_count.f_gse_id = t_single_cell.f_gse_id
    );


DROP VIEW IF EXISTS t_distinct_cell_type;
CREATE VIEW t_distinct_cell_type AS
(
SELECT DISTINCT `t_cluster_cell_count`.`f_gse_id`    AS `f_gse_id`,
                `t_cluster_cell_count`.`f_cell_type` AS `f_cell_type`,
                SUM(`t_cluster_cell_count`.`f_count`) AS `f_count`
FROM t_cluster_cell_count
GROUP BY `t_cluster_cell_count`.`f_gse_id`,
         t_cluster_cell_count.f_cell_type
    );


DROP VIEW IF EXISTS t_sample_cell_type;
CREATE VIEW t_sample_cell_type AS
(
SELECT DISTINCT `t_cluster_anno`.`f_gse_id`    AS `f_gse_id`,
                `t_cluster_anno`.`f_gsm_id`    AS `f_gsm_id`,
                `t_cluster_anno`.`f_cluster`   AS `f_cluster`,
                `t_cluster_anno`.`f_cell_type` AS `f_cell_type`
FROM t_cluster_anno
GROUP BY `t_cluster_anno`.`f_gse_id`,
         `t_cluster_anno`.`f_gsm_id`,
         `t_cluster_anno`.`f_cluster`,
         t_cluster_anno.f_cell_type
    );




