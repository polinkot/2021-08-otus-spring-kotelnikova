
const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

    function loadOwners() {
        $.get('/api/v1/owners').done(function (owners) {
            $('#owners > tbody').empty();

            owners.forEach(function (owner) {
                $('#owners > tbody').append(`
                    <tr>
                        <td>${owner.id}</td>
                        <td>${owner.name}</td>
                        <td>${owner.age}</td>
                        <td>${owner.address}</td>
                        <td>${owner.phone}</td>
                        <td>
                            <a href="#" onclick="editOwner(${owner.id})">Редактировать</a>
                            <a href="#" onclick="deleteOwner(${owner.id})">Удалить</a>
                        </td>
                    </tr>
                `)
            });
        })
    }

    function loadAnimals(ownerId) {
        if (!ownerId) {
            return;
        }

        $.get('/api/v1/owners/' + ownerId + '/animals').done(function (animals) {
            $('#comments > tbody').empty();

            animals.forEach(function (animal) {
                $('#animals > tbody').append(`
                        <tr>
                            <td>${animal.id}</td>
                            <td>${animal.name}</td>
                            <td>${animal.type == 'CAT' ? 'Кошка' : 'Собака'}</td>
                        </tr>
                    `)
            });
        })
    }

    function clearEditBlock() {
        $('#ownerId').val('');
        $('#ownerName').val('');
        $('#ownerAge').val('');
        $('#ownerAddress').val('');
        $('#ownerPhone').val('');
    }

    function showList() {
        $('#ownersLink').hide();
        $('#ownersNoLink').show();

        $('#ownerEditor').hide();
        clearEditBlock();

        loadOwners();
        $('#ownerList').show();
    }

    function showEditor(ownerId) {
        $('#ownersLink').show();
        $('#ownersNoLink').hide();

        $('#ownerList').hide();
        $('#ownerEditor').show();
    }

    function editOwner(id) {
        showEditor(id);

        $.get('/api/v1/owners/' + id).done(function (owner) {
            $('#ownerId').val(id);
            $('#ownerName').val(owner.name);
            $('#ownerAge').val(owner.age);
            $('#ownerAddress').val(owner.address);
            $('#ownerPhone').val(owner.phone);
        });

        loadAnimals(id);
    }

    function saveOwner() {
        let id = $('#ownerId').val();
        let method = (id === '') ? 'POST' : 'PUT';

        $.ajax({
            url: '/api/v1/owners',
            type: method,
            data: JSON.stringify({
                id: id,
                name: $('#ownerName').val(),
                age: $('#ownerAge').val(),
                address: $('#ownerAddress').val(),
                phone: $('#ownerPhone').val()
            }),
            headers: {'X-XSRF-TOKEN': csrfToken},
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function () {
                showList();
            }
        });
    }

    function deleteOwner(id) {
        if (!confirm('Вы уверены?')) {
            return;
        }

        $.ajax({
            url: '/api/v1/owners/' + id,
            type: 'DELETE',
            headers: {'X-XSRF-TOKEN': csrfToken},
            success: function () {
                loadOwners();
            }
        });
    }