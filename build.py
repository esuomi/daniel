#!/usr/bin/env python

import sh
import re
import string

class Builder(object):

    def is_num(self, obj):
        try:
            int(obj)
            return True
        except ValueError:
            return False

    def __init__(self):
        pass

    def run(self):
        version = re.compile(r'[/ (](v\d+\.\d+\.\d+)')
        for commit in sh.git("--no-pager", "log", pretty="format:%H %d", color="never", _iter_noblock=True):
            if not self.is_num(commit):
                parts = commit.strip(string.whitespace).split()
                if len(parts) == 2:
                    hash = parts[0]
                    refs = parts[1]
                    #print(hash + " >> " + refs)
                    match = version.search(refs, 0)
                    if match:
                        with open("gradle.properties", "w") as build_props:
                            build_props.write("parentVersion={0}\n".format(match.group(1)))
                            build_props.write("parentHash=" + hash)
                        break
                #else:
                    #print(commit + " did not match")

if __name__ == "__main__":
    b = Builder()
    b.run()
