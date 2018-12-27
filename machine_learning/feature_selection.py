import numpy as np
from sklearn.externals import  joblib
import pandas as pd
# df = pd.read_csv("redfin_data.House.csv")
#
#
#
#
# feature_df = pd.get_dummies(df, columns=['zip','property_type'])
# del feature_df['price']
# print(list(feature_df))



feature_labels =np.array([
    'baths',
    'beds',
    'days_on_market',
    'hoa',
    'lot_size',
    'square_feet',
    'year_build',
    'zip_95008',
    'zip_95032',
    'zip_95035',
    'zip_95037',
    'zip_95070',
    'zip_95110',
    'zip_95111',
    'zip_95112',
    'zip_95116',
    'zip_95117',
    'zip_95118',
    'zip_95119',
    'zip_95120',
    'zip_95121',
    'zip_95122',
    'zip_95123',
    'zip_95124',
    'zip_95125',
    'zip_95126',
    'zip_95127',
    'zip_95128',
    'zip_95129',
    'zip_95130',
    'zip_95131',
    'zip_95132',
    'zip_95133',
    'zip_95134',
    'zip_95135',
    'zip_95136',
    'zip_95138',
    'zip_95139',
    'zip_95141',
    'zip_95148',
    'property_type_Condo/Co-op',
    'property_type_Mobile/Manufactured Home',
    'property_type_Multi-Family (2-4 Unit)',
    'property_type_Multi-Family (5+ Unit)',
    'property_type_Other',
    'property_type_Single Family Residential',
    'property_type_Townhouse',
    'property_type_Vacant Land'])
model = joblib.load('MachineLearning/trained_house_classifier_model.pkl')


home_to_value =[
  2.5,
    3,
  40,
    348,
    1590,
    1537,
    32,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    1,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    0,
    1,
    0
]

homes_to_value = [
    home_to_value
]

# Run the model and make a prediction for each house in the homes_to_value array
predicted_home_values = model.predict(homes_to_value)

# Since we are only predicting the price of one house, just look at the first prediction returned
predicted_value = predicted_home_values[0]

print("This house has an estimated value of ${:,.2f}".format(predicted_value))