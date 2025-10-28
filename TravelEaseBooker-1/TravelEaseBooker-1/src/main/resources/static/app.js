const API_BASE = '/api';

let currentBookingData = null;

function showTab(tabName) {
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    document.getElementById(tabName).classList.add('active');
    event.target.classList.add('active');
}

async function loadAllFlights() {
    try {
        const response = await fetch(`${API_BASE}/flights`);
        const flights = await response.json();
        displayFlights(flights);
    } catch (error) {
        console.error('Error loading flights:', error);
        showError('flightResults', 'Failed to load flights');
    }
}

async function searchFlights() {
    const origin = document.getElementById('flightOrigin').value;
    const destination = document.getElementById('flightDestination').value;
    
    if (!origin || !destination) {
        alert('Please enter both origin and destination');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/flights/search?origin=${origin}&destination=${destination}`);
        const flights = await response.json();
        displayFlights(flights);
    } catch (error) {
        console.error('Error searching flights:', error);
        showError('flightResults', 'Failed to search flights');
    }
}

function displayFlights(flights) {
    const container = document.getElementById('flightResults');
    
    if (flights.length === 0) {
        container.innerHTML = '<div class="no-results">No flights found</div>';
        return;
    }
    
    container.innerHTML = flights.map(flight => `
        <div class="card">
            <h3>${flight.flightNumber} - ${flight.airline}</h3>
            <div class="card-detail"><strong>Route:</strong> ${flight.origin} → ${flight.destination}</div>
            <div class="card-detail"><strong>Departure:</strong> ${new Date(flight.departureTime).toLocaleString()}</div>
            <div class="card-detail"><strong>Arrival:</strong> ${new Date(flight.arrivalTime).toLocaleString()}</div>
            <div class="card-detail"><strong>Class:</strong> ${flight.cabinClass}</div>
            <div class="card-detail"><strong>Available Seats:</strong> ${flight.availableSeats}</div>
            <div class="price">$${flight.price.toFixed(2)}</div>
            <button class="book-btn" onclick='bookService("FLIGHT", ${JSON.stringify(flight)})'>Book Now</button>
        </div>
    `).join('');
}

async function loadAllHotels() {
    try {
        const response = await fetch(`${API_BASE}/hotels`);
        const hotels = await response.json();
        displayHotels(hotels);
    } catch (error) {
        console.error('Error loading hotels:', error);
        showError('hotelResults', 'Failed to load hotels');
    }
}

async function searchHotels() {
    const location = document.getElementById('hotelLocation').value;
    const minStars = document.getElementById('hotelStars').value;
    
    if (!location) {
        alert('Please enter a location');
        return;
    }
    
    try {
        let url = `${API_BASE}/hotels/search?location=${location}`;
        if (minStars) {
            url += `&minStars=${minStars}`;
        }
        const response = await fetch(url);
        const hotels = await response.json();
        displayHotels(hotels);
    } catch (error) {
        console.error('Error searching hotels:', error);
        showError('hotelResults', 'Failed to search hotels');
    }
}

function displayHotels(hotels) {
    const container = document.getElementById('hotelResults');
    
    if (hotels.length === 0) {
        container.innerHTML = '<div class="no-results">No hotels found</div>';
        return;
    }
    
    container.innerHTML = hotels.map(hotel => `
        <div class="card">
            <h3>${hotel.name}</h3>
            <div class="card-detail"><strong>Location:</strong> ${hotel.location}</div>
            <div class="card-detail"><strong>Address:</strong> ${hotel.address}</div>
            <div class="card-detail"><strong>Rating:</strong> ${'⭐'.repeat(hotel.starRating)}</div>
            <div class="card-detail"><strong>Room Type:</strong> ${hotel.roomType}</div>
            <div class="card-detail"><strong>Amenities:</strong> ${hotel.amenities}</div>
            <div class="card-detail"><strong>Available Rooms:</strong> ${hotel.availableRooms}</div>
            <div class="price">$${hotel.pricePerNight.toFixed(2)}/night</div>
            <button class="book-btn" onclick='bookService("HOTEL", ${JSON.stringify(hotel)})'>Book Now</button>
        </div>
    `).join('');
}

async function loadAllTransportation() {
    try {
        const response = await fetch(`${API_BASE}/transportation`);
        const transportation = await response.json();
        displayTransportation(transportation);
    } catch (error) {
        console.error('Error loading transportation:', error);
        showError('transportResults', 'Failed to load transportation');
    }
}

async function searchTransportation() {
    const location = document.getElementById('transportLocation').value;
    const type = document.getElementById('transportType').value;
    
    if (!location) {
        alert('Please enter a location');
        return;
    }
    
    try {
        let url = `${API_BASE}/transportation/search?location=${location}`;
        if (type) {
            url += `&type=${type}`;
        }
        const response = await fetch(url);
        const transportation = await response.json();
        displayTransportation(transportation);
    } catch (error) {
        console.error('Error searching transportation:', error);
        showError('transportResults', 'Failed to search transportation');
    }
}

function displayTransportation(transportation) {
    const container = document.getElementById('transportResults');
    
    if (transportation.length === 0) {
        container.innerHTML = '<div class="no-results">No transportation options found</div>';
        return;
    }
    
    container.innerHTML = transportation.map(transport => `
        <div class="card">
            <h3>${transport.type} - ${transport.provider}</h3>
            <div class="card-detail"><strong>Location:</strong> ${transport.location}</div>
            <div class="card-detail"><strong>Vehicle:</strong> ${transport.vehicleModel}</div>
            <div class="card-detail"><strong>Capacity:</strong> ${transport.passengerCapacity} passengers</div>
            <div class="card-detail"><strong>Features:</strong> ${transport.features}</div>
            <div class="card-detail"><strong>Available Units:</strong> ${transport.availableUnits}</div>
            <div class="price">$${transport.pricePerDay.toFixed(2)}/day</div>
            <button class="book-btn" onclick='bookService("TRANSPORTATION", ${JSON.stringify(transport)})'>Book Now</button>
        </div>
    `).join('');
}

function bookService(serviceType, serviceData) {
    currentBookingData = {
        serviceType: serviceType,
        serviceId: serviceData.id,
        serviceData: serviceData
    };
    
    let details = '';
    if (serviceType === 'FLIGHT') {
        details = `
            <h3>Flight Details</h3>
            <p><strong>Flight:</strong> ${serviceData.flightNumber} - ${serviceData.airline}</p>
            <p><strong>Route:</strong> ${serviceData.origin} → ${serviceData.destination}</p>
            <p><strong>Departure:</strong> ${new Date(serviceData.departureTime).toLocaleString()}</p>
            <p><strong>Price:</strong> $${serviceData.price.toFixed(2)}</p>
        `;
    } else if (serviceType === 'HOTEL') {
        details = `
            <h3>Hotel Details</h3>
            <p><strong>Hotel:</strong> ${serviceData.name}</p>
            <p><strong>Location:</strong> ${serviceData.location}</p>
            <p><strong>Room Type:</strong> ${serviceData.roomType}</p>
            <p><strong>Price:</strong> $${serviceData.pricePerNight.toFixed(2)}/night</p>
        `;
    } else if (serviceType === 'TRANSPORTATION') {
        details = `
            <h3>Transportation Details</h3>
            <p><strong>Type:</strong> ${serviceData.type}</p>
            <p><strong>Provider:</strong> ${serviceData.provider}</p>
            <p><strong>Vehicle:</strong> ${serviceData.vehicleModel}</p>
            <p><strong>Price:</strong> $${serviceData.pricePerDay.toFixed(2)}/day</p>
        `;
    }
    
    document.getElementById('bookingDetails').innerHTML = details;
    document.getElementById('bookingModal').style.display = 'block';
}

document.getElementById('bookingForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const name = document.getElementById('bookingName').value;
    const email = document.getElementById('bookingEmail').value;
    
    const booking = {
        serviceType: currentBookingData.serviceType,
        serviceId: currentBookingData.serviceId,
        customerName: name,
        customerEmail: email,
        totalPrice: currentBookingData.serviceType === 'FLIGHT' ? currentBookingData.serviceData.price :
                    currentBookingData.serviceType === 'HOTEL' ? currentBookingData.serviceData.pricePerNight :
                    currentBookingData.serviceData.pricePerDay,
        bookingDetails: JSON.stringify(currentBookingData.serviceData)
    };
    
    try {
        const response = await fetch(`${API_BASE}/bookings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(booking)
        });
        
        const result = await response.json();
        
        closeBookingModal();
        showConfirmation(result);
        
        document.getElementById('bookingForm').reset();
    } catch (error) {
        console.error('Error creating booking:', error);
        alert('Failed to create booking. Please try again.');
    }
});

function showConfirmation(booking) {
    document.getElementById('confirmationDetails').innerHTML = `
        <div style="text-align: center; padding: 20px;">
            <h3 style="color: #27ae60; margin-bottom: 20px;">✓ Booking Successful!</h3>
            <p><strong>Booking Reference:</strong></p>
            <h2 style="color: #667eea; margin: 10px 0;">${booking.bookingReference}</h2>
            <p style="margin-top: 20px;">Please save this reference number for your records.</p>
            <p><strong>Confirmation sent to:</strong> ${booking.customerEmail}</p>
            <p style="margin-top: 20px; color: #666;">Total: $${booking.totalPrice.toFixed(2)}</p>
        </div>
    `;
    document.getElementById('confirmationModal').style.display = 'block';
}

async function loadMyBookings() {
    const email = document.getElementById('customerEmail').value;
    
    if (!email) {
        alert('Please enter your email');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/bookings/customer/${email}`);
        const bookings = await response.json();
        displayBookings(bookings);
    } catch (error) {
        console.error('Error loading bookings:', error);
        showError('bookingResults', 'Failed to load bookings');
    }
}

async function searchBookingByRef() {
    const ref = document.getElementById('bookingRef').value;
    
    if (!ref) {
        alert('Please enter a booking reference');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/bookings/reference/${ref}`);
        if (response.ok) {
            const booking = await response.json();
            displayBookings([booking]);
        } else {
            showError('bookingResults', 'Booking not found');
        }
    } catch (error) {
        console.error('Error searching booking:', error);
        showError('bookingResults', 'Failed to search booking');
    }
}

function displayBookings(bookings) {
    const container = document.getElementById('bookingResults');
    
    if (bookings.length === 0) {
        container.innerHTML = '<div class="no-results">No bookings found</div>';
        return;
    }
    
    container.innerHTML = bookings.map(booking => `
        <div class="booking-card ${booking.status.toLowerCase()}">
            <h3>Booking Reference: ${booking.bookingReference}</h3>
            <span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span>
            <div class="card-detail"><strong>Service Type:</strong> ${booking.serviceType}</div>
            <div class="card-detail"><strong>Customer:</strong> ${booking.customerName}</div>
            <div class="card-detail"><strong>Email:</strong> ${booking.customerEmail}</div>
            <div class="card-detail"><strong>Booking Date:</strong> ${new Date(booking.bookingDate).toLocaleString()}</div>
            <div class="card-detail"><strong>Total Price:</strong> $${booking.totalPrice.toFixed(2)}</div>
            ${booking.status === 'CONFIRMED' ? `
                <button class="book-btn cancel-btn" onclick="cancelBooking('${booking.bookingReference}')">Cancel Booking</button>
            ` : ''}
        </div>
    `).join('');
}

async function cancelBooking(reference) {
    if (!confirm('Are you sure you want to cancel this booking?')) {
        return;
    }
    
    try {
        await fetch(`${API_BASE}/bookings/cancel/${reference}`, {
            method: 'PUT'
        });
        
        alert('Booking cancelled successfully');
        document.getElementById('bookingRef').value = reference;
        searchBookingByRef();
    } catch (error) {
        console.error('Error cancelling booking:', error);
        alert('Failed to cancel booking');
    }
}

function closeBookingModal() {
    document.getElementById('bookingModal').style.display = 'none';
}

function closeConfirmationModal() {
    document.getElementById('confirmationModal').style.display = 'none';
}

function showError(containerId, message) {
    document.getElementById(containerId).innerHTML = `
        <div class="no-results" style="color: #e74c3c;">${message}</div>
    `;
}

window.onclick = function(event) {
    const bookingModal = document.getElementById('bookingModal');
    const confirmationModal = document.getElementById('confirmationModal');
    if (event.target === bookingModal) {
        closeBookingModal();
    }
    if (event.target === confirmationModal) {
        closeConfirmationModal();
    }
}

loadAllFlights();
