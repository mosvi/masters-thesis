import glob
import pandas as pd
import os
from sklearn import preprocessing
import numpy as np

# specifying the path to csv files
path = 'F:\\Thesis\\project\\data-collection\\test-smells\\'

# csv files in the path
files = glob.glob(path + "/*.csv")

# checking all the csv files in the
# specified path
for filename in files:

    #storing csv file name
    file_name = os.path.split(os.path.abspath(filename))

	
    df = pd.read_csv(filename, index_col=None)
    
    x = np.array(df['Commit'])
    scaler = preprocessing.MinMaxScaler()
    d = scaler.fit_transform(x[:, np.newaxis])
    scaled_df = pd.DataFrame(d, columns=['Commit'])
    df['Commit'] = np.round(scaled_df['Commit'].to_numpy('float'),2)
    df.to_csv(path+file_name[1], index=False) 
    print('done')