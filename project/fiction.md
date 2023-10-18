https://www.v2ex.com/t/415604

# 命名实体识别

难点: 
1. 需要识别武器，门派等特殊实体，因此常规的标注数据集不够用.
2. 人物有很多的外号


## 训练语料
基于规则做对8个

使用了金庸12本小说作为数据集.

基于jieba分词，筛选出名词，基于规则从中过滤出一部分的想要的武器，门派实体(比如名词+)


## 外号识别

例如，
在《射雕英雄传》中“郭靖”也被称为“郭大侠”，但此 处以“郭靖”作为ＢＮ；又比如《笑傲江湖》中，“任盈 盈”也被称为“盈盈”。虽“盈盈”出现频数远大于“任 盈盈”，但在外号识别时，“盈盈”会作为外号，所以此 处会取“任盈盈”为ＢＮ


外号指示词是指在武侠小说中出现外号时常常出现 的词。本文选取了“外号”“绰号”“人称”“号称”“称 作”等词汇作为ＮＩ。

候选外号子串指的是在寻找 ＢＮ的外号时最有可能是ＢＮ的外号的子串。并对 ＯＮＳ进行迭代，以找出ＢＮ的外号

观察窗口(OW)是指在选取标识词语后，选择当前位置的 前后ｎ（ｎ＞０）个位置上的字、词、字母、数字、标点 和其标识表示的集合

固定句式法则是指在统计并定位了 ＮＩ之后，在ＯＷ 中对外号进行查找时，要根据一些 固定句式或特征来进行外号识别。

由于单纯的分词不能准确找到外号，容易将外号拆分开，故将外号出现的句子进行整理归纳。遇到这 些句式时，对字符串进行摘取作为 ＯＮＳ。

实体在句子中的顺序和距离、词汇特征(包括组成实体的词、出现在实体上下文中的动词等)以及链接语法(Link grammar)[65]分析结果


## 模型算法
采用CRF， 自定义特征函数，特征权重自动学习.
CRF无向图模型，直接通过势函数拟合后验分布.

HMM等价于某个特定的CRF（定义适当的特征函数）

# 实体关系提取

自己提取特征，之后采用SVM训练关系分类模型.
https://github.com/KavinKKuppusamy/NLP-Classifying-Semantic-Relation-between-Entities-using-LinearSVM

关系种类:
人物之间，师徒，父子，父女....