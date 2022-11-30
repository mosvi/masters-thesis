import glob
import pandas as pd
import os


def getTotalClasses(dataFrame):
    
    temp=0
    prevComm=''
    newComm=''
    counter=0

    for ind in dataFrame.index:
        newComm = dataFrame['Commit Hash'][ind]
        if prevComm==newComm :
            counter+=1 
        else:
            prevComm=newComm
            temp+=1 
            if(temp<2):
                counter+=1 
            else:
                break

 
    # returning counter
    return counter

# specifying the path to csv files
#F:\Thesis\project\dataset\android-specific-smells
path = "F:\\Thesis\\project\\dataset\\ck-metrics\\"

# csv files in the path
files = glob.glob(path + "/*.csv")


# checking all the csv files in the
# specified path
for filename in files:

    #storing csv file name
    file_name = os.path.split(os.path.abspath(filename))

    a=''
    b=''
    i=-1

    #LOC,LCOM,CBO,WMC

    LOC = []
    LCOM = []
    CBO = []
    WMC = []
    Commit = []

	# reading content of csv file
    df = pd.read_csv(filename, index_col=None)
    p = getTotalClasses(df)
    #print(p)
    rowStart=0
    rowEnd=p

    for ind in df.index:

        if((len(df.index)+p) == rowEnd):
            break

        Commit.append(ind)
        LOC.append(df['LOC'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        LCOM.append(df['LCOM'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        CBO.append(df['CBO'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        WMC.append(df['WMC'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))

        rowStart = rowStart + p
        rowEnd = rowEnd + p

    totalSmells = { 
        'Commit':Commit,
        'LOC' :LOC,
        'LCOM':LCOM,
        'CBO':CBO,
        'WMC':WMC,
        }
        
    newDf = pd.DataFrame(totalSmells)

    #print(newDf)
    #F:\Thesis\project\data-collection\android-specific-smells
    newDf.to_csv('F:\\Thesis\\project\\data-collection\\ck-metrics\\'+file_name[1], index=False)
    print('done')
