
# jenkins cli
jenkins bet-diff --cn --evaluation_submode="CHERRY" --full_set
jenkins bet-diff --sc --full_set --export_mode cherry_evaluation --evaluation_submode="CHERRY"
jenkins latency --filter_tags="cherry" --gz --gemini
jenkins BRT --diff=auto --cherry


