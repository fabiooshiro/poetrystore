package poetrystore



import org.junit.*
import grails.test.mixin.*

@TestFor(PoemController)
@Mock(Poem)
class PoemControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/poem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.poemInstanceList.size() == 0
        assert model.poemInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.poemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.poemInstance != null
        assert view == '/poem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/poem/show/1'
        assert controller.flash.message != null
        assert Poem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/poem/list'

        populateValidParams(params)
        def poem = new Poem(params)

        assert poem.save() != null

        params.id = poem.id

        def model = controller.show()

        assert model.poemInstance == poem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/poem/list'

        populateValidParams(params)
        def poem = new Poem(params)

        assert poem.save() != null

        params.id = poem.id

        def model = controller.edit()

        assert model.poemInstance == poem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/poem/list'

        response.reset()

        populateValidParams(params)
        def poem = new Poem(params)

        assert poem.save() != null

        // test invalid parameters in update
        params.id = poem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/poem/edit"
        assert model.poemInstance != null

        poem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/poem/show/$poem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        poem.clearErrors()

        populateValidParams(params)
        params.id = poem.id
        params.version = -1
        controller.update()

        assert view == "/poem/edit"
        assert model.poemInstance != null
        assert model.poemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/poem/list'

        response.reset()

        populateValidParams(params)
        def poem = new Poem(params)

        assert poem.save() != null
        assert Poem.count() == 1

        params.id = poem.id

        controller.delete()

        assert Poem.count() == 0
        assert Poem.get(poem.id) == null
        assert response.redirectedUrl == '/poem/list'
    }
}
