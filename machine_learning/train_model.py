import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn import ensemble
from sklearn.metrics import mean_absolute_error
from sklearn.externals import joblib

df = pd.read_csv("../redfin_data.House.csv")




feature_df = pd.get_dummies(df, columns=['zip','property_type'])
#print(feature_df.head())

#remove the sale price
del feature_df['price']

# Create the X and y arrays
X = feature_df.as_matrix()
y = df['price'].as_matrix()

#Split the datq set in a trainning set (70%)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=0)

# Fit regression model
model = ensemble.GradientBoostingRegressor(
    n_estimators=8000,
    learning_rate=0.03,
    max_depth=6,
    min_samples_leaf=7,
    max_features=0.1,
    loss='huber',
    random_state=0
)

model.fit(X_train, y_train)

# Save the trained model to a file so we can use it in other programs
joblib.dump(model, 'trained_house_classifier_model.pkl')

# Find the error rate on the training set
mse = mean_absolute_error(y_train, model.predict(X_train))
print("Training Set Mean Absolute Error: %.4f" % mse)

# Find the error rate on the test set
mse = mean_absolute_error(y_test, model.predict(X_test))
print("Test Set Mean Absolute Error: %.4f" % mse)

