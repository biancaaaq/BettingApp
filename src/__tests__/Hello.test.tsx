import React from 'react';
import { render, screen } from '@testing-library/react';
import Hello from '../components/Hello';

describe('Hello component', () => {
  it('renders greeting message', () => {
    render(<Hello name="Alex" />);
    expect(screen.getByText('Hello, Alex!')).toBeInTheDocument();
  });
});
