/**
 * @author Coder ACJHP
 * @Email hexa.octabin@gmail.com
 * @Date 15/07/2017
 */
package com.coder.hms.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.coder.hms.connection.DataSourceFactory;
import com.coder.hms.dao.ReservationDAO;
import com.coder.hms.entities.Reservation;

public class ReservationDaoImpl implements ReservationDAO{

	private Session session;
	private DataSourceFactory dataSourceFactory;
	private final Logger LOGGER = Logger.getLogger(ReservationDaoImpl.class.getName());
	
	public ReservationDaoImpl() {
		dataSourceFactory = new DataSourceFactory();
		DataSourceFactory.createConnection();
		session = dataSourceFactory.getSession();
		
	}
	
	@Override
	public Reservation findReservationById(long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation findReservationByDate(String Date) {
		Query<Reservation> query = session.createQuery("from Reservation where checkinDate=:Date", Reservation.class);
		query.setParameter("Date", Date);
		Reservation reservation = query.getSingleResult();
		return reservation;
	}

	@Override
	public void saveReservation(Reservation reservation) {
		session.save(reservation);
		session.getTransaction().commit();
	}

	@Override
	public void cancelReservation(long reservationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reservation> getAllReservations() {
		Query<Reservation> query = session.createQuery("from Reservation", Reservation.class);
		List<Reservation> reservList = query.getResultList();
		
		LOGGER.info(reservList.toString());
		
		return reservList;
	}

	public List<Reservation> getReservsByDate(String today) {
		Query<Reservation> query = session.createQuery("from Reservation where checkinDate=:today", Reservation.class);
		query.setParameter("today", today);
		List<Reservation> reservList = query.getResultList();
		return reservList;
	}

	public List<Reservation> getGaranteedReservs(String today) {
		Query<Reservation> query = session.createQuery("from Reservation "
							+ "where bookStatus = 'GUARANTEE' and checkinDate=:today", Reservation.class);
		query.setParameter("today", today);
		List<Reservation> guaranteedList = query.getResultList();
		return guaranteedList;
	}

	public List<Reservation> getReservsAsWaitlist(String today) {
		Query<Reservation> query = session.createQuery("from Reservation "
							+ "where bookStatus = 'WAITLIST' and checkinDate=:today", Reservation.class);
		query.setParameter("today", today);
		List<Reservation> waitedList = query.getResultList();
		return waitedList;
	}

}
