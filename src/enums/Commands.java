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
	
	CancellationReportRequest,

	AverageParkStayTimeCheck, OnlineBookingCapacityCheck, ParkCapacityCheck, 
	checkIfExist,

	
	CheckParkCapacity, //to check specific timeslot
	

	visitorStatisticData,

	CancellationReportData, ClientConnect, 

	visitReportRequest ,
	
	CancelNonPayedBooking,

	getVisitorAmountInPark, vistorAmountData, AddManagerRequestDetail, getRequestTable, RequestTableData,
	removeRequest, RequestRemoved,



	CheckSixSlots, CurrentOccupancy, MaxOccupancy, BookingDetails, 



	visitorReportData, 


	 AddReport, AddReportCheck, getReportTable, SetReportList,



	ChangePaymentStatusInDB, EnterPark, ExitPark



	;
}


