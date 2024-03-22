package enums;
//Client
public enum Commands {

	CheckWorkerLogin,CheckVisitorLogin,
	VisitorLogOut,

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
	visitorStatisticData, 
	
	CheckParkCapacity //to check specific timeslot
	

	;
}


