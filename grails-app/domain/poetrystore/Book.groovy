package poetrystore

class Book {

	static belongsTo = [poet: Poet]

	String name

    static constraints = {
    }
}
