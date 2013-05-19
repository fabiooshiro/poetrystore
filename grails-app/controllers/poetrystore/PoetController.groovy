package poetrystore

import org.springframework.dao.DataIntegrityViolationException

class PoetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [poetInstanceList: Poet.list(params), poetInstanceTotal: Poet.count()]
    }

    def create() {
        [poetInstance: new Poet(params)]
    }

    def save() {
        def poetInstance = new Poet(params)
        if (!poetInstance.save(flush: true)) {
            render(view: "create", model: [poetInstance: poetInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'poet.label', default: 'Poet'), poetInstance.id])
        redirect(action: "show", id: poetInstance.id)
    }

    def show(Long id) {
        def poetInstance = Poet.get(id)
        if (!poetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "list")
            return
        }

        [poetInstance: poetInstance]
    }

    def edit(Long id) {
        def poetInstance = Poet.get(id)
        if (!poetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "list")
            return
        }

        [poetInstance: poetInstance]
    }

    def update(Long id, Long version) {
        def poetInstance = Poet.get(id)
        if (!poetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (poetInstance.version > version) {
                poetInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'poet.label', default: 'Poet')] as Object[],
                          "Another user has updated this Poet while you were editing")
                render(view: "edit", model: [poetInstance: poetInstance])
                return
            }
        }

        poetInstance.properties = params

        if (!poetInstance.save(flush: true)) {
            render(view: "edit", model: [poetInstance: poetInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'poet.label', default: 'Poet'), poetInstance.id])
        redirect(action: "show", id: poetInstance.id)
    }

    def delete(Long id) {
        def poetInstance = Poet.get(id)
        if (!poetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "list")
            return
        }

        try {
            poetInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'poet.label', default: 'Poet'), id])
            redirect(action: "show", id: id)
        }
    }
}
