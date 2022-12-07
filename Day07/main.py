import util


class Folder:
    def __init__(self, name, parent):
        self.name = name
        self.parent = parent
        self.files = list()
        self.folders = list()

    def get_size(self):
        size = sum(f.size for f in self.files)
        size += sum(f.get_size() for f in self.folders)
        return size


class File:
    def __init__(self, name, size):
        self.name = name
        self.size = size


lines = util.input_as_strings()
lines.pop(0)  # remove cd to root

folders = list()
root = Folder("/", None)
folders.append(root)
cwd = root

# Parse and build file and directory tree
for line in lines:
    if line.startswith("$ cd "):
        name = line.replace("$ cd ", "")
        if name == "..":
            cwd = cwd.parent
        else:
            cwd = next(f for f in cwd.folders if f.name == name)
    if line.startswith("dir "):
        folder = Folder(line.replace("dir ", ""), cwd)
        cwd.folders.append(folder)
        folders.append(folder)
    if line[0].isnumeric():
        parts = line.split(" ")
        file = File(parts[1], int(parts[0]))
        cwd.files.append(file)

# Part 1
folders_with_size_below_threshold = (f.get_size() for f in folders if f.get_size() <= 100000)
print(f'Part 1: {sum(folders_with_size_below_threshold)}')  # 1427048

# Part 2
needed_space = 30000000 - (70000000 - root.get_size())
folders.sort(key=lambda x: x.get_size())
folder_to_delete = next(f for f in folders if f.get_size() >= needed_space)
print(f'Part 2: {folder_to_delete.get_size()}')  # 2940614
