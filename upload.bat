@echo off
start cmd /c "scp -r ${project_path}\target\sc2GWAS-1.0.0.war root@bio.liclab.net:${code_path}/sc2GWAS_service.war"
