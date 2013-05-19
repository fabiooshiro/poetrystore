package poetrystore

import org.springframework.dao.DataIntegrityViolationException

class BookController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [poemInstanceList: Book.list(params), poemInstanceTotal: Book.count()]
    }

    def create() {
        [poemInstance: new Book(params)]
    }

    def save() {
        def poemInstance = new Book(params)
        if (!poemInstance.save(flush: true)) {
            render(view: "create", model: [poemInstance: poemInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'poem.label', default: 'Book'), poemInstance.id])
        redirect(action: "show", id: poemInstance.id)
    }

    def show(Long id) {
        def poemInstance = Book.get(id)
        if (!poemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [poemInstance: poemInstance]
    }

    def edit(Long id) {
        def poemInstance = Book.get(id)
        if (!poemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        [poemInstance: poemInstance]
    }

    def update(Long id, Long version) {
        def poemInstance = Book.get(id)
        if (!poemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (poemInstance.version > version) {
                poemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'poem.label', default: 'Book')] as Object[],
                          "Another user has updated this Book while you were editing")
                render(view: "edit", model: [poemInstance: poemInstance])
                return
            }
        }

        poemInstance.properties = params

        if (!poemInstance.save(flush: true)) {
            render(view: "edit", model: [poemInstance: poemInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'poem.label', default: 'Book'), poemInstance.id])
        redirect(action: "show", id: poemInstance.id)
    }

    def delete(Long id) {
        def poemInstance = Book.get(id)
        if (!poemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "list")
            return
        }

        try {
            poemInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'poem.label', default: 'Book'), id])
            redirect(action: "show", id: id)
        }
    }
}
