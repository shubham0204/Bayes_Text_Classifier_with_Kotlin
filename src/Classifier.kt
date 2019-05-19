


class Classifier( private var positiveBagOfWords : Array<String> , private var negativeBagOfWords : Array<String>) {

    companion object {

        val CLASS_POSITIVE = 0
        val CLASS_NEGATIVE = 1

        private val englishStopWords = arrayOf(
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves",
            "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their",
            "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was",
            "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the",
            "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against",
            "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in",
            "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why",
            "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no",
            "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should",
            "now" , "hello" , "hi" , "dear" , "respected" , "thank"
        )

        fun getTokens( text : String ) : Array<String> {
            val tokens = text.toLowerCase().split( " " )
            val filteredTokens = ArrayList<String>()
            for ( i in 0 until tokens.count() ) {
                if ( !tokens[i].trim().isBlank() ) {
                    filteredTokens.add( tokens[ i ] )
                }
            }
            val stopWordRemovedTokens = tokens.map { Regex("[^a-zA-Z]").replace( it , "") }
                    as ArrayList<String>
            stopWordRemovedTokens.removeAll {
                englishStopWords.contains( it ) or it.trim().isBlank()
            }
            return stopWordRemovedTokens.distinct().toTypedArray()
        }

    }

    fun classifyText( text : String ) : Int {
        val class_1_probability = findProbabilityGivenSample( text , positiveBagOfWords )
        val class_2_probability = findProbabilityGivenSample( text , negativeBagOfWords )
        if ( class_1_probability > class_2_probability ) {
            return CLASS_POSITIVE
        }
        else {
            return CLASS_NEGATIVE
        }
    }

    private fun findProbabilityGivenSample( document : String , classVocab : Array<String> ) : Float {
        val tokens = getTokens( document )
        var probability_given_class = 1.0f
        for ( token in tokens ) {
            probability_given_class *= findProbabilityGivenClass( token , classVocab )
        }
        return probability_given_class * findClassProbability( positiveBagOfWords , negativeBagOfWords , classVocab )
    }

    private fun findProbabilityGivenClass( x : String , classVocab : Array<String> ) : Float {
        val x_count = classVocab.count { it.contains(x) or x.contains(it) }.toFloat()
        val class_count = classVocab.count().toFloat()
        return (( x_count / class_count ) + 1)
    }

    private fun findClassProbability(classVocab1 : Array<String>, classVocab2 : Array<String> , classVocab : Array<String>) : Float{
        val total_words = (classVocab1.count() + classVocab2.count()).toFloat()
        val class_count = (classVocab.count()).toFloat()
        return ( class_count / total_words )
    }


}

