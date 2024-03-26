package enums;
//Client
public enum Commands {

	CheckWorkerLogin,CheckVisitorLogin,
	VisitorLogOut,
	WorkerLogOut,

	VisitorMyBooking,
	CheckIfGroupGuide,
	CancelBooking,

	
	WorkerLoginResult, VisitorLoginResult,
	
	
	ChangeParkCapacity,ChangeOnlineBookingCapacity,ChangeAverageParkStayTime,


	newBookingToDB//to enter to db
	,
	VisitorStatisticRequest,


	AverageParkStayTimeCheck, OnlineBookingCapacityCheck, ParkCapacityCheck, 
	checkIfExist,

	
	CheckParkCapacity, //to check specific timeslot
	

	visitorStatisticData,
	CancellationReportData, ClientConnect, 

	CheckSixSlots, CancelNonPayedBooking,

	getVisitorAmountInPark, vistorAmountData, AddManagerRequestDetail, getRequestTable, RequestTableData,
	removeRequest, RequestRemoved


	;
}


