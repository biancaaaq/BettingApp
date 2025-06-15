import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import TicketSidebar from '../components/TicketSidebar';
import { BrowserRouter } from 'react-router-dom';
import { Cota } from '../context/BetSlipContext';
import api from '../services/api';

const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => mockNavigate,
}));
jest.mock('../services/api');

const mockCote: Cota[] = [
  { id: 1, valoare: 2.5, descriere: 'Echipa 1 câștigă', idMeci: 101, blocat: false },
  { id: 2, valoare: 3.1, descriere: 'Egal', idMeci: 102, blocat: false },
];

describe('TicketSidebar - teste simple', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const renderComponent = (props = {}) => {
    render(
      <BrowserRouter>
        <TicketSidebar selectedCote={mockCote} onClear={jest.fn()} {...props} />
      </BrowserRouter>
    );
  };

  it('afișează cotele și câștigul estimat corect', () => {
    renderComponent();
    fireEvent.change(screen.getByLabelText(/miză/i), { target: { value: '100' } });
    expect(screen.getByText(/Câștig potențial:/i)).toHaveTextContent('775.00');
  });

it('trimite biletul și redirecționează corect', async () => {
  jest.useFakeTimers(); 
  (api.post as jest.Mock).mockResolvedValueOnce({
    status: 200,
    data: { id: 1 }, // răspuns simulat
  });

  renderComponent();

  fireEvent.change(screen.getByLabelText(/miză/i), { target: { value: '50' } });
  fireEvent.click(screen.getByRole('button', { name: /Pariază acum/i }));

  // Avansează timpul cu 2 secunde ca să declanșeze navigate
  await waitFor(() => {
    expect(api.post).toHaveBeenCalled();
  });

  jest.advanceTimersByTime(2000); // avansează timpul

  await waitFor(() => {
    expect(mockNavigate).toHaveBeenCalledWith('/bilete/mele');
  });

  jest.useRealTimers(); // revino la ceasul real
});


  it('nu trimite biletul dacă nu există cote', () => {
    render(
      <BrowserRouter>
        <TicketSidebar selectedCote={[]} onClear={jest.fn()} />
      </BrowserRouter>
    );

    const button = screen.getByRole('button', { name: /Pariază acum/i });
    // în loc să testăm `disabled`, ne asigurăm că nu face nimic
    fireEvent.click(button);
    expect(api.post).not.toHaveBeenCalled();
  });
});
