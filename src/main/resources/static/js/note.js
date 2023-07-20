$(document).ready(function(){
	$('#submit').on('click', function(){
		const content = $(".content").val();

		$.ajax({
			url: "/add",
			type: "POST",
			data: {
				"content" : content
			},
			dataType: "json"
		})
		.done(function(data) {
			const data_stringify = JSON.stringify(data);
			const data_json = JSON.parse(data_stringify);
			const data_id = data_json["id"];
			const content = data_json["content"];
			let data_count = Number(data_json["like_count"]); 

			$("#addContent").after(`
			<div class="addContent${data_id}">
				<h4>${content}</h4>
				<div  style="display:inline;" class="edit${data_id}">
					<a action="/Edit/${data_id}" method="get">
					<button type="button" onclick="getEditFunk('${data_id}')">編集</button>
					</a>
				</div>
				<form action="/Delete/${data_id}" method="delete" style="display:inline;">
					<button type="button" class="btn btn-outline-danger" onclick="deleteContent('${data_id}')">削除</button>
				</form>
				<a action="/Count/${data_id}" method="get">
				<p style="display: inline;"><span id ="count${data_id}">${data_count}</span></p>
					<input type="button" onclick="countLike('${data_id}')" value="いいね" style="display:inline">
				</a>
			</div>`);

			$(".content").val("");
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			alert("error!");
			console.log("jqXHR          : " + jqXHR.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		})
	})
})

function getEditFunk(id) {

	const edit_url = "/Edit/" + id;

	$.ajax({
		url: edit_url,
		type: "GET",
		data: {
			"id": id
		},
		dataType: "json"
	})
	.done(function(data) {

		const data_id = data["id"];
		const content = data["content"];
		const edit_btn = ".edit" + data_id;

		$(edit_btn).before(`
		<div style="display:inline;" class="update${data_id}">
			<a action="/Update/${data_id}" method="put">
				<button type="button" onclick="UpdateFunk('${data_id}')">更新</button>
			</a>
		</div>`);
		$(edit_btn).remove();

		const content_edit = "#content" + data_id;
		$(content_edit).after(`
		    <div id="content${data_id}" style="margin: 1rem 0;">
				<input type="text" name="content" size="20" maxlength="200" id="update_content${data_id}"
		    	value="${content}"/>
		    </div>`);
		$(content_edit).remove();
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		alert("error!");
		console.log("jqXHR          : " + jqXHR.status);
		console.log("textStatus     : " + textStatus);
		console.log("errorThrown    : " + errorThrown.message);
	})

};
function UpdateFunk(id) {

	const updata_url = "/Updata/" + id;
	const update_value = "#update_content" + id;
	const update_content = $(update_value).val();

	$.ajax({
		url: updata_url,
		type: "PUT",
		data: {
			"id": id,
			"content": update_content
		},
		dataType: "json"
	})
	.done(function(data) {
		const data_stringify = JSON.stringify(data);
		const data_json = JSON.parse(data_stringify);
		const data_id = data_json["id"];
		const content = data_json["content"];
		const update_btn = ".update" + data_id;
		$(update_btn).before(`
		<div style="display:inline;" class="edit${data_id}">
			<a action="/Edit/${data_id}" method="get">
				<button type="button" onclick="getEditFunk('${data_id}')">編集</button>
			</a>
		</div>`);
		$(update_btn).remove();
		const content_update = "#content" + data_id;
		$(content_update).after(`
		    <div id="content${data_id}" style="margin: 1rem 0;">
				<h4>${content}</h4>
		    </div>`);
		$(content_update).remove();
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		alert("error!");
		console.log("jqXHR          : " + jqXHR.status);
		console.log("textStatus     : " + textStatus);
		console.log("errorThrown    : " + errorThrown.message);
	})

};
function deleteContent(id) {
	const delete_url = "/Delete/" + id;

	if(window.confirm('本当に削除しますか?')){
		$.ajax({
			url: delete_url,
			type: "delete",
			data: {
				id: id,
			}
		})
		.done(function() {
			const deleteContent = ".addContent" + id;

			$(deleteContent).remove();
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			alert("error!");
			console.log("jqXHR          : " + jqXHR.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		})
		return true;
	}
	else{
		window.confirm('キャンセルしました');
		console.log(delete_url);
		return false;
	}
};
function countLike(id) {

	const countUp_url = "/Count/" + id;

	$.ajax({
		url: countUp_url,
		type: "GET",
		data: {
			"id": id,
		},
		dataType: "json"
	})
	.done(function(data) {
		const data_stringify = JSON.stringify(data);
		const data_json = JSON.parse(data_stringify);
		const data_id = data_json["id"];
		const count_id ="#count" + data_id;
		let likeCount = $(count_id).html();
			likeCount = Number(likeCount);
			likeCount = likeCount + 1;
		$("#count" + id).html(likeCount);
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		alert("error!");
		console.log("jqXHR          : " + jqXHR.status);
		console.log("textStatus     : " + textStatus);
		console.log("errorThrown    : " + errorThrown.message);
	})
};