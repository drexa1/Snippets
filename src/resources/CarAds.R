library(rjson)
library(tm)
library(wordcloud)
library(ggplot2)
library(RColorBrewer)

cat("\n")
cat("---------------------------------------------------------- \n")
cat(" Exploratory of Trovit car adds \n")
cat("---------------------------------------------------------- \n")

# Read JSON lines
# Apparently the file path needs to be absolute, please use your folder here
f <- file("C:/Users/DR186049/git/trovit/src/assets/cars.small.json", "r")
lines <- readLines(f, -1L)
cars <- lapply(X=lines, fromJSON)

# Pick the contentChunk field (the last one)
text <- lapply (cars, function(line) line[length(line)])

# Create volative corpus
corpus <- VCorpus(VectorSource(text))
corpus <- tm_map(corpus, function(x) iconv(x, to='UTF-8', sub='byte'))

# Apply transformations manually
corpus_clean <- tm_map(corpus, tolower)
corpus_clean <- tm_map(corpus_clean, PlainTextDocument)
corpus_clean <- tm_map(corpus_clean, removePunctuation)
corpus_clean <- tm_map(corpus_clean, removeNumbers)
corpus_clean <- tm_map(corpus_clean, stripWhitespace)
# Irrelevant words
corpus_clean <- tm_map(corpus_clean, removeWords, stopwords())
# Stemming
corpus_clean <- tm_map(corpus_clean, stemDocument)

# Tokenize into term matrix
dtm <- DocumentTermMatrix(corpus_clean)
# Flatten to max 20% empty space matrix
dtms <- removeSparseTerms(dtm, 0.2)

# Find most frequent terms
freq <- findFreqTerms(dtm, lowfreq=5)

# Draw pretty wordcloud
set.seed(123)
wordcloud(corpus_clean, max.words=100, random.order=FALSE, rot.per=0.35, colors=brewer.pal(8, "Paired"))
