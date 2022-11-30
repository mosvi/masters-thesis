import pandas as pd
import glob

path = 'F:\\Thesis\\project\\data-collection\\test-smells\\'

# csv files in the path
files = glob.glob(path + "/*.csv")

value = []

for filename in files:

    df = pd.read_csv(filename, index_col=None)
    
    value.append(df)
    #pd.concat(df).groupby(level=0).mean()

#frame = pd.concat(value, axis=0, ignore_index=True)
frame = pd.concat(value).groupby(level=0).mean().round(2)
frame.to_csv('F:\\Thesis\\project\\result\\test-smells.csv', index=False) 
print(frame)