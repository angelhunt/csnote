bet
jenkins bet-diff --data_set SCENE_CLASSIFICATION --export_mode scene_classification_evaluation --full_set --custom-flags '--labeling_task_class="OBJECT_2D" --compare_all_scenarios' --cn

jenkins bet-diff --evaluation_submode="SCENE_CLASSIFICATION" --export_mode="scene_classification_evaluation" --full_set --custom-flags '--labeling_task_class="OBJECT_2D" --compare_all_scenarios' --cn



# cicd


jenkins premerge-check
