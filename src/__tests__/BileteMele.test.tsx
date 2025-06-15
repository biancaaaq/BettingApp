import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import BileteMele from '../components/BileteMele';
import api from '../services/api';
import { BrowserRouter } from 'react-router-dom';

jest.mock('../services/api');
jest.mock('../services/authService', () => ({
  getToken: () => 'fake-token', // simulăm utilizator logat
}));

const mockBilet = {
  id: 1,
  miza: 100,
  cotaTotala: 2.5,
  castigPotential: 250,
  status: 'ACTIVE',
  dataCreare: new Date().toISOString(),
};

describe('BileteMele - teste simple', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const renderComponent = () => {
    render(
      <BrowserRouter>
        <BileteMele />
      </BrowserRouter>
    );
  };

  it('afișează titlul principal', () => {
    renderComponent();
    expect(screen.getByText(/Biletele Mele/i)).toBeInTheDocument();
  });

  it('afișează mesajul dacă nu sunt bilete', async () => {
    (api.get as jest.Mock).mockResolvedValueOnce({ data: [] });

    renderComponent();

    expect(await screen.findByText(/Nu ai bilete active/i)).toBeInTheDocument();
  });

  it('afișează un bilet dacă există', async () => {
    (api.get as jest.Mock).mockResolvedValueOnce({ data: [mockBilet] });

    renderComponent();

    expect(await screen.findByText(/Bilet #1/i)).toBeInTheDocument();
    expect(screen.getByText(/100 RON/)).toBeInTheDocument();
    expect(screen.getByText(/2.5/)).toBeInTheDocument();
  });
});
