import sys,pandas as pd
import matplotlib.pyplot as plt

path=sys.argv[1] if len(sys.argv)>1 else "results.csv"
df=pd.read_csv(path)
print(df.to_string(index=False))
ax=df.pivot(index="workload",columns="strategy",values="hit_rate").plot(kind="bar")
ax.set_ylabel("Hit rate");ax.set_title("Cache strategy hit rate by workload")
plt.tight_layout();plt.savefig("hit-rate.png",dpi=160)
