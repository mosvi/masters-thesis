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
path = "F:\\Thesis\\project\\dataset\\test-smells\\"

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

    #Assertion_Roulette,Eager_Test,General_Fixture,Mystery_Guest,Resource_Optimistism

    Assertion_Roulette = []
    Eager_Test = []
    General_Fixture = []
    Mystery_Guest = []
    Resource_Optimistism = []
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
        Assertion_Roulette.append(df['Assertion_Roulette'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        Eager_Test.append(df['Eager_Test'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        General_Fixture.append(df['General_Fixture'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        Mystery_Guest.append(df['Mystery_Guest'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))
        Resource_Optimistism.append(df['Resource_Optimistism'].iloc[rowStart:rowEnd].sum(axis=0,  numeric_only=True))

        rowStart = rowStart + p
        rowEnd = rowEnd + p

    totalSmells = { 
        'Commit':Commit,
        'Assertion_Roulette' :Assertion_Roulette,
        'Eager_Test':Eager_Test,
        'General_Fixture':General_Fixture,
        'Mystery_Guest':Mystery_Guest,
        'Resource_Optimistism':Resource_Optimistism,
        }
        
    newDf = pd.DataFrame(totalSmells)

    #print(newDf)
    #F:\Thesis\project\data-collection\android-specific-smells
    newDf.to_csv('F:\\Thesis\\project\\data-collection\\test-smells\\'+file_name[1], index=False)
    print('done')
