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
path = "F:\\Thesis\\project\\dataset\\method-smells\\"

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

    #Decor_Feature_Envy,Decor_long_Method

    Decor_Feature_Envy = []
    Decor_long_Method = []
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
        Decor_Feature_Envy.append(df['Decor_Feature_Envy'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        Decor_long_Method.append(df['Decor_long_Method'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))

        rowStart = rowStart + p
        rowEnd = rowEnd + p

    totalSmells = { 
        'Commit':Commit,
        'Decor_Feature_Envy' :Decor_Feature_Envy,
        'Decor_long_Method':Decor_long_Method,
        }
        
    newDf = pd.DataFrame(totalSmells)

    #print(newDf)
    #F:\Thesis\project\data-collection\android-specific-smells
    newDf.to_csv('F:\\Thesis\\project\\data-collection\\method-smells\\'+file_name[1], index=False)
    print('done')
