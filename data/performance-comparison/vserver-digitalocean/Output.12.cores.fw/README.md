Calculate average for all entries:


for i in perf-acewiki-*
                                                 echo "$i" 
                                                 cat "$i" |  grep -oP "[:0-9\.]+el" | sed -e "s/el//g" |  xargs | sed -e 's/ / \& /g' | python2 -c "import sys; l = map(lambda x: x.strip(), sys.stdin.readlines()); ll = list(map(lambda x: x.split(' & '), l))[0]; lll = map(lambda x: x.split(':'), ll); llll = map(lambda x: int(x[0])*60 + float(x[1]), lll); lf = reduce(lambda x,y: x+y, llll)/len(llll); lff = map(lambda x: str(int(x/60)) + ':' + str('%.2f' % round(x%60,2)), [lf]); print lff[0]"
                                             
                                             end

