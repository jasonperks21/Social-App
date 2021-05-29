import React from 'react';

function WithGroupLoading(Component) {
  return function WihLoadingComponent({ isLoading, ...props }) {
    if (!isLoading) return <Component {...props} />;
    return (
      <p style={{ textAlign: 'center'}}>
        Hold on, fetching data may take some time :)
      </p>
    );
  };
}
export default WithGroupLoading;