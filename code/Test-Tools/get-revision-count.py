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

revs = []
usernames = []
userids = []

for page in pagelist:
    if getText(page.getElementsByTagName('ns')[0]) != str(NS_TO_FILTER):
        continue

    try:
        # skip redirect pages
        title = getText(page.getElementsByTagName('title')[0])
        #id = getText(page.getElementsByTagName("id")[0])
        redirect = page.getElementsByTagName('redirect')
        #print("Page {} {} with redirect {}".format(id, title, redirect))
        if len(redirect) > 0 and ":" in title:
            #print("Skippping page {}".format(title))
            continue
    except Exception as e:
        pass
    new_page = True
    for revision in page.getElementsByTagName('revision'):
        rev_id = getText(revision.getElementsByTagName('id')[0])
        contributor = revision.getElementsByTagName('contributor')[0]

        username = None
        userid = None
        try:
            username = getText(contributor.getElementsByTagName('username')[0])
            userid = getText(contributor.getElementsByTagName('id')[0])
        except Exception as e:
            try:
                username = getText(contributor.getElementsByTagName('ip')[0])
            except:
                pass

        # if username is None:
        #     continue

        if (not new_page and usernames[-1] == username) or (not new_page and username is not None and userid is not None and userids[-1] == userid):
            del usernames[-1]
            del userids[-1]
            del revs[-1]

        revs.append(rev_id)
        usernames.append(username)
        userids.append(userid)
        new_page = False

# for rev, autor in zip(revs, usernames):
#    print("Revision: {} by {}".format(rev, autor))
for rev in revs:
    print(rev)

# user_len = len(revs)
# print("User-revisions: {}".format(user_len))
# print("Deleted-Revisions: {}".format(deleted_revs))
# print("Total: {}".format(user_len + deleted_revs))
