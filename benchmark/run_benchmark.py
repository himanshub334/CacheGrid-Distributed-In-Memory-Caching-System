import argparse, csv, random, time
from collections import OrderedDict

def workload(n, keys, kind):
    if kind=="uniform": return [random.randrange(keys) for _ in range(n)]
    if kind=="burst": return [random.randrange(max(1,keys//20)) if i%10 else random.randrange(keys) for i in range(n)]
    # Zipf-like popularity without external dependency.
    weights=[1/(i+1) for i in range(keys)]
    return random.choices(range(keys),weights=weights,k=n)

class LRU:
    def __init__(self,c): self.c=c; self.d=OrderedDict()
    def get(self,k):
        if k not in self.d:return None
        v=self.d.pop(k);self.d[k]=v;return v
    def put(self,k,v):
        if k in self.d:self.d.pop(k)
        self.d[k]=v
        if len(self.d)>self.c:self.d.popitem(last=False)

class LFU:
    def __init__(self,c):self.c=c;self.d={};self.f={}
    def get(self,k):
        if k not in self.d:return None
        self.f[k]+=1;return self.d[k]
    def put(self,k,v):
        if k in self.d:self.d[k]=v;self.f[k]+=1;return
        if len(self.d)>=self.c:
            victim=min(self.d,key=lambda x:self.f[x]);self.d.pop(victim);self.f.pop(victim)
        self.d[k]=v;self.f[k]=1

def run(cls,n,keys,capacity):
    c=cls(capacity); hits=0; lat=[]
    ops=workload(n,keys,"zipf")
    start=time.perf_counter_ns()
    for k in ops:
        t=time.perf_counter_ns(); v=c.get(k)
        if v is not None:hits+=1
        else:c.put(k,k)
        lat.append(time.perf_counter_ns()-t)
    total=time.perf_counter_ns()-start
    s=sorted(lat)
    pct=lambda p:s[min(len(s)-1,int(len(s)*p))]
    return hits/n, pct(.50)/1000,pct(.95)/1000,pct(.99)/1000,n/(total/1e9)

def main():
    ap=argparse.ArgumentParser();ap.add_argument("--operations",type=int,default=50000);ap.add_argument("--keys",type=int,default=5000);ap.add_argument("--capacity",type=int,default=1000);ap.add_argument("--output",default="results.csv");a=ap.parse_args()
    rows=[]
    for workload_name in ["zipf","uniform","burst"]:
        for name,cls in [("LRU",LRU),("LFU",LFU)]:
            # Temporarily generate each workload explicitly.
            c=cls(a.capacity);ops=workload(a.operations,a.keys,workload_name);lat=[];hits=0;t0=time.perf_counter_ns()
            for k in ops:
                t=time.perf_counter_ns();v=c.get(k)
                if v is not None:hits+=1
                else:c.put(k,k)
                lat.append(time.perf_counter_ns()-t)
            s=sorted(lat);pct=lambda p:s[min(len(s)-1,int(len(s)*p))]
            rows.append([workload_name,name,hits/a.operations,pct(.5)/1000,pct(.95)/1000,pct(.99)/1000,a.operations/((time.perf_counter_ns()-t0)/1e9)])
    with open(a.output,"w",newline="") as f:
        w=csv.writer(f);w.writerow(["workload","strategy","hit_rate","p50_us","p95_us","p99_us","ops_sec"]);w.writerows(rows)
    print("wrote",a.output)

if __name__=="__main__":main()
