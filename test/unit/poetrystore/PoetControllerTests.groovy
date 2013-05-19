package poetrystore



import org.junit.*
import grails.test.mixin.*

@TestFor(PoetController)
@Mock(Poet)
class PoetControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/poet/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.poetInstanceList.size() == 0
        assert model.poetInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.poetInstance != null
    }

    void testSave() {
        controller.save()

        assert model.poetInstance != null
        assert view == '/poet/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/poet/show/1'
        assert controller.flash.message != null
        assert Poet.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/poet/list'

        populateValidParams(params)
        def poet = new Poet(params)

        assert poet.save() != null

        params.id = poet.id

        def model = controller.show()

        assert model.poetInstance == poet
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/poet/list'

        populateValidParams(params)
        def poet = new Poet(params)

        assert poet.save() != null

        params.id = poet.id

        def model = controller.edit()

        assert model.poetInstance == poet
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/poet/list'

        response.reset()

        populateValidParams(params)
        def poet = new Poet(params)

        assert poet.save() != null

        // test invalid parameters in update
        params.id = poet.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/poet/edit"
        assert model.poetInstance != null

        poet.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/poet/show/$poet.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        poet.clearErrors()

        populateValidParams(params)
        params.id = poet.id
        params.version = -1
        controller.update()

        assert view == "/poet/edit"
        assert model.poetInstance != null
        assert model.poetInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/poet/list'

        response.reset()

        populateValidParams(params)
        def poet = new Poet(params)

        assert poet.save() != null
        assert Poet.count() == 1

        params.id = poet.id

        controller.delete()

        assert Poet.count() == 0
        assert Poet.get(poet.id) == null
        assert response.redirectedUrl == '/poet/list'
    }
}
