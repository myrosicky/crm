<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script> document.title = '工作台';</script>

	<span name="title" class="thinBorder">我的工作台</span>
	<div id="summary">
		<span name="title">今日概要 <span id="summaryTimezone"><label >时间段</label><input type="text" id="from" name="from"><label >-</label><input type="text" id="to" name="to"></span> </span>
		<ul name="grids" class="ulPanel">
			<li class="grid">
				<a href="#" ><div>20</div><p>未联系客户数量</p></a>
			</li>
			<li class="grid">
				<a href="#" ><div>30</div><p>已联系客户数量</p></a>
			</li>
		</ul>
		
		<span name="title">最近一周工作情况</span>
		<ul name="graphics" class="ulPanel">
			<li>
				<canvas id="barChart" class="barChart"></canvas>
			</li>
			<li >
				<canvas id="barChart2" class="barChart"></canvas>
			</li>
		</ul>
		
		<span name="title">客户分析统计 </span>
		<ul class="ulPanel">
			<li class="grid">
				<a href="#" ><div>20</div><p>未联系客户数量</p></a>
			</li>
			<li  class="grid">
				<a href="#" ><div>20</div><p>未联系客户数量</p></a>
			</li>
			<li >
				<canvas id="pieChart" class="barChart"></canvas>
			</li>
			
		</ul>
		
	</div>
	
	<div id="rightPanel" class="thinBorder">
		<div id="missionPanel" >
			<div >任务</div>
			<ul data-bind="template: {name:'missions-template', foreach:missions} " >
			</ul>
		</div>
		<div id="recentlyContactClients">
			<div>最近联系客户</div>
			<ul data-bind="template: {name:'clients-template', foreach: clients} " >
			</ul>
		</div>
	</div>

<script type="text/html"  id="missions-template">
	<li><span data-bind="text: name">-</span><a href="#" data-bind="click: $parent.edit.bind($data,id)" >修改</a></li>
</script>

<script type="text/html"  id="clients-template">
	<li><a href="#" data-bind="text: name, click: $parent.edit.bind($data,id)" >-</a></li>
</script>



<script>

	var ctx = $("#barChart")[0].getContext("2d"),
		ctx2 = $("#barChart2")[0].getContext("2d")
		ctx3 = $("#pieChart")[0].getContext("2d")
	;
	
	var data = {
	    labels: ["January", "February", "March", "April", "May", "June", "July"],
	    datasets: [
	        {
	            label: "My First dataset",
	            fillColor: "rgba(220,220,220,0.5)",
	            strokeColor: "rgba(220,220,220,0.8)",
	            highlightFill: "rgba(220,220,220,0.75)",
	            highlightStroke: "rgba(220,220,220,1)",
	            data: [65, 59, 80, 81, 56, 55, 40]
	        },
	        {
	            label: "My Second dataset",
	            fillColor: "rgba(151,187,205,0.5)",
	            strokeColor: "rgba(151,187,205,0.8)",
	            highlightFill: "rgba(151,187,205,0.75)",
	            highlightStroke: "rgba(151,187,205,1)",
	            data: [28, 48, 40, 19, 86, 27, 90]
	        }
	    ]
	};
	
	var options = {
	    //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
	    scaleBeginAtZero : true,
	
	    //Boolean - Whether grid lines are shown across the chart
	    scaleShowGridLines : true,
	
	    //String - Colour of the grid lines
	    scaleGridLineColor : "rgb(218, 147, 140)",
	
	    //Number - Width of the grid lines
	    scaleGridLineWidth : 1,
	
	    //Boolean - Whether to show horizontal lines (except X axis)
	    scaleShowHorizontalLines: false,
	
	    //Boolean - Whether to show vertical lines (except Y axis)
	    scaleShowVerticalLines: false,
	
	    //Boolean - If there is a stroke on each bar
	    barShowStroke : true,
	
	    //Number - Pixel width of the bar stroke
	    barStrokeWidth : 2,
	
	    //Number - Spacing between each of the X value sets
	    barValueSpacing : 5,
	
	    //Number - Spacing between data sets within X values
	    barDatasetSpacing : 1,
	
	    
	};
	
	var barChart = new Chart(ctx).Bar(data, options),
		barChart2 = new Chart(ctx2).Bar(data, options),
		pieChart = new Chart(ctx3).Pie([
    {
        value: 300,
        color:"#F7464A",
        highlight: "#FF5A5E",
        label: "Red"
    },
    {
        value: 50,
        color: "#46BFBD",
        highlight: "#5AD3D1",
        label: "Green"
    },
    {
        value: 100,
        color: "#FDB45C",
        highlight: "#FFC870",
        label: "Yellow"
    }
], {
    //Boolean - Whether we should show a stroke on each segment
    segmentShowStroke : true,

    //String - The colour of each segment stroke
    segmentStrokeColor : "#fff",

    //Number - The width of each segment stroke
    segmentStrokeWidth : 2,

    //Number - The percentage of the chart that we cut out of the middle
    percentageInnerCutout : 50, // This is 0 for Pie charts

    //Number - Amount of animation steps
    animationSteps : 100,

    //String - Animation easing effect
    animationEasing : "easeOutBounce",

    //Boolean - Whether we animate the rotation of the Doughnut
    animateRotate : true,

    //Boolean - Whether we animate scaling the Doughnut from the centre
    animateScale : false,

    
});
		
	var	missionModel = new function (){
			var self = this;
			self.missions = ko.observable([]) ;
			self.retrieveMissions = function(){
				$.ajax({
					url:""
					,data: ""
					,success:function(missions){
						self.missions(missions);
					}
				});
				
			};
			self.edit = function(id,data){
				
			}
	};
			
			$(function(){
				$( "#from" ).datepicker({
				      defaultDate: "+1w",
				      changeMonth: true,
				      numberOfMonths: 2,
				      onClose: function( selectedDate ) {
				        $( "#to" ).datepicker( "option", "minDate", selectedDate );
				      }
				    });
				    $( "#to" ).datepicker({
				      defaultDate: "+1w",
				      changeMonth: true,
				      numberOfMonths: 2,
				      onClose: function( selectedDate ) {
				        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
				      }
				    });
				
				try{
					ko.applyBindings(missionModel, $("#missionPanel")[0]);
				}catch(e){}
				
				
				
			});

</script>
