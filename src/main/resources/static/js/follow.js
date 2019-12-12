function follow(check, userId, i){
	// true -> follow 하기
	// false -> unFollow 하기
	let url = "/follow/"+userId;
	if(check){
		fetch(url,{
			method: "POST"
	    }).then(function(res){
			return res.text();							
		}).then(function(res){
			if(res === "ok"){
				let follow_item_el = document.querySelector("#follow_item_"+i);
				follow_item_el.innerHTML = `<button class='following_btn' onClick="follow(false, ${userId}, ${i})">팔로잉</button>`;
			}
		});
	}else{
		fetch(url,{
			method: "DELETE"
	    }).then(function(res){
			return res.text();							
		}).then(function(res){
			if(res === "ok"){
				let follow_item_el = document.querySelector("#follow_item_"+i);
				follow_item_el.innerHTML = `<button class='follow_btn' onClick="follow(true, ${userId}, ${i})">팔로우</button>`;
			}
		});
	}
}