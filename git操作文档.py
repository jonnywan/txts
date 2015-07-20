
# Git 通常有两种方式来进行初始化:
'''
git clone: 这是较为简单的一种初始化方式，当你已经有一个远程的Git版本库，只需要在本地克隆一份

例如：git clone git://github.com/someone/some_project.git  some_project 

上面的命令就是将'git://github.com/someone/some_project.git'这个URL地址的远程版本库完全克隆到本地some_project目录下面


git init和git remote：这种方式稍微复杂一些，当你本地创建了一个工作目录，你可以进入这个目录，使用'git init'命令进行初始化，Git以后就会对该目录下的文件进行版本控制，这时候如果你需要将它放到远程服务器上，可以在远程服务器上创建一个目录，并把 可访问的URL记录下来，此时你就可以利用'git remote add'命令来增加一个远程服务器端，

例如：git remote add origin git://github.com/someone/another_project.git

上面的命令就会增加URL地址为'git: //github.com/someone/another_project.git'，名称为origin的远程服务器，以后提交代码的时候只需要使用 origin别名即可

'''

# 创建一个本地代码库
'''
作为例子，我们会假装我们有一个网站（无所谓技术）存在于我们机器上的‘workspace’文件夹下的’my_site’文件夹内。在命令行中，去到你的站点的根文件夹。在OS X和Linux上：
cd ~/workspace/my_site/
在Windows上：
cd c:\workspace\my_site
我们首先需要告诉Git这个文件夹是我们需要跟踪的项目。所以我们发送这个命令来初始化一个新的本地Git代码库
git init
Git会在my_site文件夹内创建一个名为.git的隐藏文件夹，那就是你的本地代码库。
'''

# 加载（Stage）文件
'''
我们现在需要命令Git我们需要加载（stage）所有项目文件。发送：
git add .
最后的“.”符号的意思是“所有文件、文件夹和子文件夹”。假如我们只想要把特定文件添加到源代码控制中去，我们可以指定它们：
git add my_file, my_other_file
'''

# 提交文件
'''
现在，我们想要提交已加载（staged）的文件。阅读“添加一个时间点，在这里你的文件处在一个可还原的状态”。我们提交我们的文件时，总是附带着有意义的注释，描述了它们现在的状态。我一直用“initial commit”来作为第一个提交的注释。

git commit -m "initial commit"
就这样。现在你随时都可以回滚到这个提交状态。如果你有需要检查你现在的已加载（staged）和未加载（unstaged）文件的状态、提交等，你可以询问git的状态：
git status
'''


