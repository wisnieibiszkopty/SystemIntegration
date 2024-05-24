import pandas as pd

df = pd.read_csv("../backend/src/main/resources/data/SteamCharts.csv")

#
df['avg_peak_perc'] = df['avg_peak_perc'].str.replace('%', '').astype(float) / 100

# month_map = {
#     'February': 1, 'January': 2, 'March': 3, 'April': 4, 'May': 5, 'June': 6,
#     'July': 7, 'August': 8, 'September': 9, 'October': 10, 'November': 11, 'December': 12
# }

# df['avg'] = df['avg'].fillna(0)
df['gain'] = df['gain'].fillna(0)
df['peak'] = df['peak'].fillna(0)

# month from string to number
#df['month'] = df['month'].map(month_map)

#df_numeric = df.apply(pd.to_numeric, errors='coerce')
# average of columns except nan
#column_means = df_numeric.mean()

# Converting nan to average
#df_filled = df_numeric.fillna(column_means)

# saving csv
df.to_csv('../backend/src/main/resources/data/SteamModified.csv', index=False)

print(df)