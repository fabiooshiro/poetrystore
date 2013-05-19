import poetrystore.*;

class BootStrap {

	def init = { servletContext ->
		if(Poet.count() == 0){
			def camoes = new Poet(name: 'Luís Vaz de Camões').save(failOnError: true)
			new Book(name: 'Rimas', poet: camoes).save(failOnError: true)
			new Book(name: 'Os Lusíadas', poet: camoes).save(failOnError: true)

			def aAzevedo = new Poet(name: 'Álvares de Azevedo').save(failOnError: true)
			new Book(name: 'Lira dos Vinte Anos', poet: aAzevedo).save(failOnError: true)
			new Book(name: 'Noite na Taverna', poet: aAzevedo).save(failOnError: true)
			
			def date = new Date('2012/01/01')
			500.times{ dx ->
				Book.list().eachWithIndex{ book, i->
					new BookSale(book: book, price: i+1.11, quantity: (i+1) * 2, date: date + dx).save(failOnError: true)
					new BookSale(book: book, price: i+1.2, quantity: (i+1), date: date + dx).save(failOnError: true)
				}
			}
		}
	}

	def destroy = {
	}
}
