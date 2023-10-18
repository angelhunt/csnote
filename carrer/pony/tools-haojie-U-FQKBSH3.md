# p8s
CLI to Schedule Training Jobs on Kubernetes Cluster


# bazel

buildmode  -c dbg/opt

# jenkins

jenkins run-ci --merge

jenkins merge

jenkins run-ci
jenkins another command


cherry 



# ASan/TSan

只检查runtime异常，需要用到单元测试

# Gperftools

性能检测


# vault
make8-bin/common/tools/vault/vault_tool login


# ml infra
http://wiki.corp.pony.ai/display/~jianshengzhu/ML+Infra+onboard

## k8s.

app. Our production k8s app configs are located in common/kubernetes/apps. 

Dashboard.
make8 build k8s_cli
make8-bin/common/tools/k8s/k8s_cli dashboard ${context}


kubectl get pods.
kubectl --context {{ context }}; kubectl config use-context {{ region }};  region as context

kubectl -n/--namespace {{ namespace }} to specify namespace. Namespace helps divide and isolate cluster resources.

check task status, kubectl get pods -l user=zhenyuyang  (-o wide (show more info))

access bash on the server. kubectl exec -it YourTaskName -- /bin/bash

get pod infomation. kubectl describe pods haojie-jupyter-6789cdc558-bjwdx

p8s create pvc --size=10Ti, p8s create jupyter

p8s --pvc_mnt=jiacheng-ceph-pvc:/home/pony-trainer/jiacheng  用于指定挂载的



 p8s create mpi \
    --replicas=1 \
    --num_gpus=4 \
    --num_cpus=32 \
    --num_rams=64Gi \
    --pvc_mnt=jiacheng-ceph-pvc:/mnt/jiacheng \
    --job_name=psd-csp-kerasengine-4 \
    --mpm_target=//perception/training/tensorflow/point_seg_det:train_range_view_segmentation_mpm \
    -- \
    --tfrecords_dir=${remote_root}/data/psd/tfrecords_20210831_shuffle \
    --checkpoint_dir=${remote_root}/checkpoints/psd/cspdarknet_keras_engine \
    --full_set_dir=${remote_root}/data/psd/tfrecords_20210831/test \
    --metric_condition_json=${remote_root}/psd_config_conditions.json \
    --imbalanced_data_dir=${remote_root}/data/psd/tfrecords_20210831_extend_imbalanced_data \
    --config_path=perception/training/tensorflow/point_seg_det/configs/exp/range_view_segmentation/cspdarknet_deconvfpn_fold1_ohemloss.prototxt


由于 infra 将 PVC 的 inode 数量限制从 1M 减少到 250K，大家的任务可能会出现 Disk quota exceeded 的情况，请大家按照一下步骤进 jupyter 删除 PVC 里面一些没用的文件，把文件数量减下来
删除你的 jupyter
重启你的 jupyter 并且将 PVC 的默认挂载位置覆盖掉: p8s create jupyter --pvc_mnt=xxx-ceph-pvc:/mnt/pvc
进入你的 jupyter 并删除你 PVC 里面一些没用的文件，PVC 的目录在 /mnt/pvc
删除你的 jupyter
重启你的 jupyter: p8s create jupyter (不需要 pvc_mnt 了)

https://ponyai.slack.com/archives/CM451TW9L/p1633934604231200


# Copy a file/folder to/from a specific pod
kubectl cp -n <namespace> --context=dx <local_path> <pod_name>:<remote_path>  # local → remote
kubectl cp -n <namespace> --context=dx <pod_name>:<remote_path> <local_path>  # remote → local
 


http://wiki.corp.pony.ai/display/~zhenyu.yang/p8s+and+kubectl
http://wiki.corp.pony.ai/display/PER/k8s+operations


dashboard

make8-bin/common/tools/k8s/k8s_cli dashboard 



# bet-launch

选项要在 simluate-args里面加


# jira

(vehicle_id ~ gz151 OR vehicle_id ~ gz152 OR vehicle_id ~ gz153) AND "Video Link"  Class(es) Generated" != OBJECT_2D AND text ~ "收费站" AND ( created < "2021/07/07 22:00" OR created > "2021/08/15 6:00") ORDER BY created DESC


(vehicle_id ~ gz151 OR vehicle_id ~ gz152 OR vehicle_id ~ gz153) AND text ~ "雨"  AND ( created > "2021/09/12 23:59") ORDER BY created 

vehicle_id ~ b104 AND created > 2022-02-28 AND issuetype in ("Hard Brake", Disengagement, Hashtag, "Uncomfortable Brake") AND status not in (Duplicate, "Closed with no action") AND project = RTI AND "Sensitive Record Path" is not EMPTY AND component = Cherry-Perception-Segmentation-Overseg



data-sync --dest daxing
data-sync --dest daxing --sensitive

https://wiki.corp.pony.ai/display/DATA/Batch+Sync+SH+issue+to+DX+HDFS

tensorboard \
--logdir=${CHECKPOINT_DIR}/summary

# dataflow
p8s create --hdfs_sensitive_data_access mpi


# git and p8
https://wiki.corp.pony.ai/pages/viewpage.action?pageId=47320245

https://wiki.corp.pony.ai/display/~zhu.wang/Useful+jenkins+and+p8+command


# olive
批量运行simulation

make8-bin/common/webapps/cloud_based_simulation/clients/jira_rti_batch_evaluation_cli run_batch_simulation --simple_mode prediction \
--submitter zhourui@pony.ai --onboard_suite_uri http://storage.corp.pony.ai/santaclara/release_v2/onboard_suite/release_v2_20210913/release_v2_20210913_RC100/ubuntu1804_onboard_suite_release_v2_20210913_RC100 \
--rti_numbers RTI-2401589,RTI-2896829,RTI-2980040,RTI-3000712,RTI-2808491


make8-bin/common/webapps/cloud_based_simulation/clients/jira_rti_batch_evaluation_cli run_batch_simulation --simple_mode pnc \
--submitter zhourui@pony.ai --run_with_master \
--rti_numbers RTI-3998623,RTI-3998736,RTI-3043523,RTI-3054127,RTI-3315191,RTI-3447906,RTI-3722935,RTI-3801603,RTI-3828162,RTI-3863829,RTI-3867243,RTI-3876037,RTI-3881050,RTI-3881265,RTI-3901551,RTI-3940441,RTI-3940665,RTI-3940709,RTI-3989046


