# style

To format some file:
clang-format -style=file [<file> ...]
Note that the -style=file option tells clang-format to make use of our pre-configured style file at .sub-repos/.clang-format

We also provide a script to format changed lines only:
common/scripts/clang_format_diff.sh

You may also set it up with your IDE.
Please refer to “IDE Setup” in last section.
