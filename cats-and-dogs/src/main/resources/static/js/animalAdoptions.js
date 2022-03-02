
const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');


function loadAdoptions() {
    $.get('/api/v1/adoptions').done(function (adoptions) {
        $('#adoptions > tbody').empty();

        adoptions.forEach(function (adoption) {
            $('#adoptions > tbody').append(`
                    <tr>
                        <td>${adoption.id}</td>
                        <td>${adoption.owner.name}</td>
                        <td>${adoption.animal.name}</td>
                        <td>
                            <a href="#" onclick="deleteAdoption(${adoption.id})">Удалить</a>
                        </td>
                    </tr>
                `)
        });
    })
}

function loadOwners() {
    $.get('/api/v1/owners').done(function (owners) {
        $('#adoptionOwnerId').empty();

        owners.forEach(function (owner) {
            $('#adoptionOwnerId').append(`
                        <option value="${owner.id}">${owner.name}</option>
                `)
        });
    })
}

function loadAnimals() {
    $.get('/api/v1/animals').done(function (animals) {
        $('#adoptionAnimalId').empty();

        animals.forEach(function (animal) {
            $('#adoptionAnimalId').append(`
                        <option value="${animal.id}">${animal.name}</option>
                `)
        });
    })
}

function clearEditBlock() {
    $('#adoptionId').val('');
    $('#adoptionOwnerId').val('');
    $('#adoptionAnimalId').val('');
}

function showList() {
    $('#adoptionsLink').hide();
    $('#adoptionsNoLink').show();

    $('#adoptionEditor').hide();
    clearEditBlock();

    loadAdoptions();
    $('#adoptionList').show();
}

function showEditor(adoptionId) {
    $('#adoptionsLink').show();
    $('#adoptionsNoLink').hide();

    loadOwners();
    loadAnimals();

    $('#adoptionList').hide();
    $('#adoptionEditor').show();
}

function saveAdoption() {
    let id = $('#adoptionId').val();

    $.ajax({
        url: '/api/v1/adoptions',
        type: 'POST',
        data: JSON.stringify({
            id: id,
            ownerId: $('#adoptionOwnerId').val(),
            animalId: $('#adoptionAnimalId').val()
        }),
        headers: {'X-XSRF-TOKEN': csrfToken},
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function () {
            showList();
        }
    });
}

function deleteAdoption(id) {
    if (!confirm('Вы уверены?')) {
        return;
    }

    $.ajax({
        url: '/api/v1/adoptions/' + id,
        type: 'DELETE',
        headers: {'X-XSRF-TOKEN': csrfToken},
        success: function () {
            loadAdoptions();
        }
    });
}