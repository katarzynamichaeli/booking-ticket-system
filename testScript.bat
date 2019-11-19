curl -H "Accept:application/json" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/screenings/1574424000000/1574442000000

curl -H "Accept:application/json" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/availableseats/s/66

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json;charset=UTF-8" -d "{\"name\":\"Jan\",\"surname\":\"Kowalski\",\"screeningId\":66,\"bookedSeats\":[{\"seatId\":60,\"ticketTypeId\":1},{\"seatId\":61,\"ticketTypeId\":2},{\"seatId\":62,\"ticketTypeId\":2}]}" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/confirm

curl -X PUT -H "Accept:application/json" -H "Content-Type: application/json" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/voucher/r/1/v/25uW8dQEnJ

curl -H "Accept: application/json" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/reservation/1

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"name\":\"Anna\",\"surname\":\"Nowak\",\"screeningId\":66,\"bookedSeats\":[{\"seatId\":70,\"ticketTypeId\":1},{\"seatId\":72,\"ticketTypeId\":2}]}" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/confirm

curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"name\":\"anna\",\"surname\":\"Nowak\",\"screeningId\":66,\"bookedSeats\":[{\"seatId\":70,\"ticketTypeId\":1},{\"seatId\":71,\"ticketTypeId\":2}]}" http://localhost:8080/TicketBookingSystem-0.0.1-SNAPSHOT/confirm
