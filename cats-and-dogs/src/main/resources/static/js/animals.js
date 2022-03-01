
const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

    function loadAnimals() {
        $.get('/api/v1/animals').done(function (animals) {
            $('#animals > tbody').empty();

            animals.forEach(function (animal) {
                $('#animals > tbody').append(`
                    <tr>
                        <td>${animal.id}</td>
                        <td>${animal.name}</td>
                        <td>${animal.age}</td>
                        <td>${animal.status}</td>
                        <td>${animal.type}</td>
                        <td>
                            <a href="#" onclick="editAnimal(${animal.id})">Edit</a>
                            <a href="#" onclick="deleteAnimal(${animal.id})">Delete</a>
                        </td>
                    </tr>
                `)
            });
        })
    }

    function clearEditBlock() {
        $('#animalId').val('');
        $('#animalName').val('');
        $('#animalGender').val('');
        $('#animalAge').val('');
        $('#animalSterilized').val('');
        $('#animalVaccinated').val('');
        $('#animalStatus').val('');
        $('#animalType').val('');
    }

    function showList() {
        $('#animalsLink').hide();
        $('#animalsNoLink').show();

        $('#animalEditor').hide();
        clearEditBlock();

        loadAnimals();
        $('#animalList').show();
    }

    function showEditor(animalId) {
        $('#animalsLink').show();
        $('#animalsNoLink').hide();

        $('#animalList').hide();
        $('#animalEditor').show();
    }

    function editAnimal(id) {
        showEditor(id);

        $.get('/api/v1/animals/' + id).done(function (animal) {
            $('#animalId').val(id);
            $('#animalName').val(animal.name);
            $('#animalGender').val(animal.gender);
            $('#animalAge').val(animal.age);
            $('#animalSterilized').val(animal.sterilized);
            $('#animalVaccinated').val(animal.vaccinated);
            $('#animalStatus').val(animal.status);
            $('#animalType').val(animal.type);
        })
    }

    function saveAnimal() {
        let id = $('#animalId').val();
        let method = (id === '') ? 'POST' : 'PUT';

        $.ajax({
            url: '/api/v1/animals',
            type: method,
            data: JSON.stringify({
                id: id,
                name: $('#animalName').val(),
                gender: $('#animalGender').val(),
                age: $('#animalAge').val(),
                sterilized: $('#animalSterilized').val(),
                vaccinated: $('#animalVaccinated').val(),
                status: $('#animalStatus').val(),
                type: $('#animalType').val()
            }),
            headers: {'X-XSRF-TOKEN': csrfToken},
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function () {
                showList();
            }
        });
    }

    function deleteAnimal(id) {
        if (!confirm('Are you sure you want to delete this animal?')) {
            return;
        }

        $.ajax({
            url: '/api/v1/animals/' + id,
            type: 'DELETE',
            headers: {'X-XSRF-TOKEN': csrfToken},
            success: function () {
                loadAnimals();
            }
        });
    }