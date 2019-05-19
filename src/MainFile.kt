
import java.util.*
import kotlin.collections.ArrayList

fun main() {

    val classifier = Classifier( Vocabulary.positiveBagOfWords , Vocabulary.negativeBagOfWords )
    println( classifier.classifyText( "insurance investment" ).toString() )
    println( classifier.classifyText( "insurance investment hi how are you we have a offer for you. Buy this and get this" +
            " one free. Thank you and have a nice day. Team xyz" ).toString() )

}





