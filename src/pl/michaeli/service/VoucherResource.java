package pl.michaeli.service;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.michaeli.model.ErrorMessage;
import pl.michaeli.model.Voucher;
import pl.michaeli.spring.config.AppConfig;
import pl.michaeli.spring.dao.ReservationsDAO;
import pl.michaeli.spring.dao.VouchersDAO;

@Path("voucher")
public class VoucherResource {

	@PUT
	@Path("r/{reservationId}/v/{voucherCode}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response addVoucher(@PathParam("reservationId") int reservationId,@PathParam("voucherCode") String voucherCode) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ReservationsDAO reservationsDAO=context.getBean(ReservationsDAO.class);
		VouchersDAO vouchersDAO=context.getBean(VouchersDAO.class);
		context.close();
		
		if(!reservationsDAO.doesExist(reservationId)) {
			ErrorMessage errorMessage = new ErrorMessage("Podana rezerwacja nie istnieje!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}

		if(new Integer(reservationsDAO.getReservationById(reservationId).getVoucherId()) != 0) {
			ErrorMessage errorMessage = new ErrorMessage("Dla tej rezerwacji naliczyliœmy ju¿ zni¿kê!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}
		
		if(!vouchersDAO.isValid(voucherCode)) {
			ErrorMessage errorMessage = new ErrorMessage("Nieprawid³owy kod!");
            return Response.serverError().type(MediaType.APPLICATION_JSON).entity(errorMessage).build();
		}

		
		vouchersDAO.putUsedState(voucherCode);
		Voucher addedVoucher = vouchersDAO.getVoucherByCode(voucherCode);
		reservationsDAO.updateReservationByVoucher(addedVoucher.getVoucherId(), reservationId);
		
		GenericEntity<Voucher> myEntity = new GenericEntity<Voucher>(addedVoucher) {};
        return Response.status(200).entity(myEntity).build();
	}
}
