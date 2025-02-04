import matplotlib.pyplot as plt
import numpy as np
import statistics

with open('/Users/jz/Desktop/毕业 project/data.txt', 'r') as file: #read data from dataset
    content = file.read()
    lines = content.split('\n')

    x1 = []; y1 = []; error1 = []
    x2 = []; y2 = []; error2 = []
    for i in range(0, len(lines)):
        print(lines[i].split())
        if i % 3 == 0:
            x1.append(int(lines[i][3:]))
            x2.append(int(lines[i][3:]))
        elif i % 3 == 1:
            strL = lines[i].split()
            intL = [int(item) for item in strL]
            mean = statistics.mean(intL)
            std = statistics.stdev(intL)
            y1.append(mean)
            error1.append(std)
        elif i % 3 == 2:
            strL = lines[i].split()
            intL = [int(item) for item in strL]
            mean = statistics.mean(intL)
            std = statistics.stdev(intL)
            y2.append(mean)
            error2.append(std)

# Plot with error bars for the first line (blue)
plt.errorbar(x1, y1, yerr=error1, fmt='--o', capsize=5, label='Bellman-Ford Algorithm', color='blue')

# Plot with error bars for the second line (red)
plt.errorbar(x2, y2, yerr=error2, fmt='-s', capsize=5, label='Floyd-Warshell Algorithm', color='red')

tick_spacing = 5
#plt.xticks(np.arange(min(x1) - 1, max(x1)+1, tick_spacing)) #display x at a span
plt.title('Sparse Graph Execution Time')
plt.xlabel('Number of Vertexes')
plt.ylabel('Execution Time (ms)')
plt.legend(loc = 0)

# Show plot
plt.show()
