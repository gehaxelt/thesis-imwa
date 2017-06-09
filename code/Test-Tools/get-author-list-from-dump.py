#!/usr/bin/python2

import xml.etree.ElementTree

FILE_TO_READ = "/tmp/aawiki.xml"
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

for page in pagelist:
    if getText(page.getElementsByTagName('ns')[0]) != str(NS_TO_FILTER):
        continue
    for revision in page.getElementsByTagName('revision'):
        contributor = revision.getElementsByTagName('contributor')[0]

        try:
            username = getText(contributor.getElementsByTagName('username')[0])
            text = getText(revision.getElementsByTagName('text')[0])
        except Exception as e:
            username = None
            text = ""
        text = text.split(r'\s+')
        if username == "" or username is None:
            print "IP-EDIT", text, len(text)
        else:
            print username, text, len(text)
