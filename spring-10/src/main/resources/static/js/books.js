    function loadBooks() {
        $.get('/books').done(function (books) {
            $('#books > tbody').empty();

            books.forEach(function (book) {
                $('#books > tbody').append(`
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.name}</td>
                        <td>${book.author.fullName}</td>
                        <td>${book.genre.name}</td>
                        <td>
                            <a href="#" onclick="editBook(${book.id})">Edit</a>
                            <a href="#" onclick="deleteBook(${book.id})">Delete</a>
                        </td>
                    </tr>
                `)
            });
        })
    }

    function loadComments(bookId) {
        if (!bookId) {
            return;
        }

        $.get('/book/' + bookId + '/comments').done(function (comments) {
            $('#comments > tbody').empty();

            comments.forEach(function (comment) {
                $('#comments > tbody').append(`
                    <tr>
                        <td>${comment.id}</td>
                        <td>${comment.text}</td>
                        <td>
                            <a href="#" onclick="deleteComment(${comment.id})">Delete</a>
                        </td>
                    </tr>
                `)
            });
        })
    }

    function loadAuthors() {
        $.get('/authors').done(function (authors) {
            $('#bookAuthorId').empty();

            authors.forEach(function (author) {
                $('#bookAuthorId').append(`
                        <option value="${author.id}">${author.fullName}</option>
                `)
            });
        })
    }

    function loadGenres() {
        $.get('/genres').done(function (genres) {
            $('#bookGenreId').empty();

            genres.forEach(function (genre) {
                $('#bookGenreId').append(`
                        <option value="${genre.id}">${genre.name}</option>
                `)
            });
        })
    }

    function clearEditBlock() {
        $('#bookId').val('');
        $('#bookName').val('');
        $('#bookAuthorId').val('');
        $('#bookGenreId').val('');
        $('#comments > tbody').empty();
    }

    function showList() {
        $('#bookEditor').hide();
        clearEditBlock();

        loadBooks();
        $('#bookList').show();
    }

    function showEditor(bookId) {
        loadAuthors();
        loadGenres();
        loadComments(bookId);

        (bookId) ? $('#commentList').show() : $('#commentList').hide();
        $('#bookList').hide();
        $('#bookEditor').show();
    }

    function editBook(id) {
        $.get('/books/' + id).done(function (book) {
            $('#bookId').val(id);
            $('#bookName').val(book.name);
            $('#bookAuthorId').val(book.author.id);
            $('#bookGenreId').val(book.genre.id);
        })

        showEditor(id);
    }

    function saveBook() {
        let id = $('#bookId').val();
        let method = (id === '') ? 'POST' : 'PUT';

        $.ajax({
            url: '/books',
            type: method,
            data: JSON.stringify({
                id: id,
                name: $('#bookName').val(),
                authorId: $('#bookAuthorId').val(),
                genreId: $('#bookGenreId').val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function () {
                showList();
            }
        });
    }

    function deleteBook(id) {
        if (!confirm('Are you sure you want to delete this book?')) {
            return;
        }

        $.ajax({
            url: '/books/' + id,
            type: 'DELETE',
            success: function () {
                loadBooks();
            }
        });
    }

    function addComment() {
        let bookId = $('#bookId').val();

        $.ajax({
            url: '/comments',
            type: 'POST',
            data: JSON.stringify({
                text: $('#commentText').val(),
                bookId: bookId
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function () {
                $('#commentText').val('');
                loadComments(bookId);
            }
        });
    }

    function deleteComment(commentId) {
        if (!confirm('Are you sure you want to delete this comment?')) {
            return;
        }

        $.ajax({
            url: '/comments/' + commentId,
            type: 'DELETE',
            success: function () {
                loadComments($('#bookId').val());
            }
        });
    }