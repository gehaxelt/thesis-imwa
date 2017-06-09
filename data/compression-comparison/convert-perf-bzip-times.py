#!/usr/bin/python2

INPUT = "perf-test-bzip.csv"
OUTPUT = "perf-test-bzip-ms.csv"

class CSVHeaderException(Exception): pass

def convert_time(time_str):
    ms = 0
    if "ms" in time_str:
        ms = int(time_str.replace("ms",""))
    elif "s" in time_str:
        ms = int(time_str.replace("s","")) * 1000
    elif "m" in time_str:
        m, s = time_str.split(":")
        ms = int(m) * 60 * 1000
        s = int(s.replace("m",""))
        ms += s*1000
    return ms

with open(OUTPUT, "w") as output:
    with open(INPUT,"r") as source:
        for line in source.readlines():
            try:
                line = line.strip()
                data = line.split(",")
                
                if not ".xml" in data[0]:
                    raise CSVHeaderException()

                for i in range(4):
                    output.write(data[i] + ",")

                for i in range(8):
                    output.write(str(convert_time(data[4+i])))
                    if i < 7:
                        output.write(",")
                output.write("\n")
            except CSVHeaderException as e:
                output.write(line + "\n")
            except Exception as e:
                print(e)