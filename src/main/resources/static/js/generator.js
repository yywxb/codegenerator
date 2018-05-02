$(function () {
   
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			tableName: null,
			part:"DO",
			result:"",
			moduleName:"",
			tablePrefix:"",
			tableNameCN:""
		}
	},
	methods: {
		generator: function() {
		
			
			$.ajax({url:"/sys/generator/code?table=" + vm.q.tableName + "&part=" + vm.q.part + "&moduleName=" 
				+ vm.q.moduleName + "&tablePrefix=" + vm.q.tablePrefix + "&tableNameCN=" + vm.q.tableNameCN,
				error:function(xhr){
					console.log("错误提示： " + xhr.status + " " + xhr.statusText);
			    },
			    success:function(result){
			    	
					console.log("response = "+ result.code);
					
				   	vm.q.result = result.code;
				}
			
			});
		},
		changePart:function(part) {
			vm.q.part = part;
			vm.generator();
		}
	}
});

