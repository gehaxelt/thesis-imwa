#!/usr/bin/python2

import xml.etree.ElementTree
import sys 

FILE_TO_READ = sys.argv[1]
NS_TO_FILTER = 0
""""
e = xml.etree.ElementTree.parse(FILE_TO_READ).getroot()

print e
for page in e:
    print list(page.find('ns'))
    if page.get('ns') != NS_TO_FILTER:
        continue

    for revision in page:
        contributor = revision.get('contributor')
        username = contributor.get('username')
        if username == "" or username is None:
            print "IP-EDIT"
        else:
            print username
"""

from xml.dom import minidom
xmldoc = minidom.parse(FILE_TO_READ)
pagelist = xmldoc.getElementsByTagName('page')

def getText(base_node):
    rc = []
    for node in base_node.childNodes:
        if node.nodeType == node.TEXT_NODE:
            rc.append(node.data)
    return ''.join(rc)

pages = []

for page in pagelist:
    if getText(page.getElementsByTagName('ns')[0]) != str(NS_TO_FILTER):
        continue
    pages.append(getText(page.getElementsByTagName('id')[0]))

for page in pages:
    print({"}".format(page))

page_len = len(pages)
print("NS-0 Pages: {}".format(page_len))
print("Total-Pages: {}".format(len(pagelist)))