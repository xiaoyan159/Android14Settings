/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.google.android.setupcompat.portal;
/**
 * Interface of progress service, all servics needs to run during onboarding, and would like
 * to consolidate notifications with SetupNotificationService, should implement this interface.
 * So that SetupNotificationService can bind the progress service and run below
 * SetupNotificationService.
 */
public interface IPortalProgressService extends android.os.IInterface
{
  /** Default implementation for IPortalProgressService. */
  public static class Default implements com.google.android.setupcompat.portal.IPortalProgressService
  {
    /** Called when the Portal notification is started. */
    @Override public void onPortalSessionStart() throws android.os.RemoteException
    {
    }
    /** Provides a non-null callback after service connected. */
    @Override public void onSetCallback(com.google.android.setupcompat.portal.IPortalProgressCallback callback) throws android.os.RemoteException
    {
    }
    /** Called when progress timed out. */
    @Override public void onSuspend() throws android.os.RemoteException
    {
    }
    /**
     * User clicks "User mobile data" on portal activity.
     * All running progress should agree to use mobile data.
     */
    @Override public void onAllowMobileData(boolean allowed) throws android.os.RemoteException
    {
    }
    /** Portal service calls to get remaining downloading size(MB) from progress service. */
    @Override public android.os.Bundle onGetRemainingValues() throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.google.android.setupcompat.portal.IPortalProgressService
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.google.android.setupcompat.portal.IPortalProgressService interface,
     * generating a proxy if needed.
     */
    public static com.google.android.setupcompat.portal.IPortalProgressService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.google.android.setupcompat.portal.IPortalProgressService))) {
        return ((com.google.android.setupcompat.portal.IPortalProgressService)iin);
      }
      return new com.google.android.setupcompat.portal.IPortalProgressService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_onPortalSessionStart:
        {
          this.onPortalSessionStart();
          break;
        }
        case TRANSACTION_onSetCallback:
        {
          com.google.android.setupcompat.portal.IPortalProgressCallback _arg0;
          _arg0 = com.google.android.setupcompat.portal.IPortalProgressCallback.Stub.asInterface(data.readStrongBinder());
          this.onSetCallback(_arg0);
          break;
        }
        case TRANSACTION_onSuspend:
        {
          this.onSuspend();
          break;
        }
        case TRANSACTION_onAllowMobileData:
        {
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.onAllowMobileData(_arg0);
          break;
        }
        case TRANSACTION_onGetRemainingValues:
        {
          android.os.Bundle _result = this.onGetRemainingValues();
          reply.writeNoException();
          _Parcel.writeTypedObject(reply, _result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.google.android.setupcompat.portal.IPortalProgressService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /** Called when the Portal notification is started. */
      @Override public void onPortalSessionStart() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onPortalSessionStart, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      /** Provides a non-null callback after service connected. */
      @Override public void onSetCallback(com.google.android.setupcompat.portal.IPortalProgressCallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onSetCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      /** Called when progress timed out. */
      @Override public void onSuspend() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onSuspend, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      /**
       * User clicks "User mobile data" on portal activity.
       * All running progress should agree to use mobile data.
       */
      @Override public void onAllowMobileData(boolean allowed) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((allowed)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_onAllowMobileData, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      /** Portal service calls to get remaining downloading size(MB) from progress service. */
      @Override public android.os.Bundle onGetRemainingValues() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        android.os.Bundle _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onGetRemainingValues, _data, _reply, 0);
          _reply.readException();
          _result = _Parcel.readTypedObject(_reply, android.os.Bundle.CREATOR);
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
    }
    static final int TRANSACTION_onPortalSessionStart = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_onSetCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_onSuspend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_onAllowMobileData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_onGetRemainingValues = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
  }
  public static final java.lang.String DESCRIPTOR = "com.google.android.setupcompat.portal.IPortalProgressService";
  /** Called when the Portal notification is started. */
  public void onPortalSessionStart() throws android.os.RemoteException;
  /** Provides a non-null callback after service connected. */
  public void onSetCallback(com.google.android.setupcompat.portal.IPortalProgressCallback callback) throws android.os.RemoteException;
  /** Called when progress timed out. */
  public void onSuspend() throws android.os.RemoteException;
  /**
   * User clicks "User mobile data" on portal activity.
   * All running progress should agree to use mobile data.
   */
  public void onAllowMobileData(boolean allowed) throws android.os.RemoteException;
  /** Portal service calls to get remaining downloading size(MB) from progress service. */
  public android.os.Bundle onGetRemainingValues() throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
